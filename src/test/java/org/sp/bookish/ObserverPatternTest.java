package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.controllers.BooksController;
import org.sp.bookish.controllers.BooksSseController;
import org.sp.bookish.controllers.NewBookRequest;
import org.sp.bookish.entity.Book;
import org.sp.bookish.observer.AllBooksSubject;
import org.sp.bookish.observer.Observer;
import org.sp.bookish.observer.SseObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ObserverPatternTest {

    @Autowired
    private AllBooksSubject allBooksSubject;

    @Autowired
    private BooksSseController booksSseController;

    @Autowired
    private BooksController booksController;

    @Test
    void testSubjectObserverRegistrationAndNotification() {
        AllBooksSubject subject = new AllBooksSubject();
        List<Book> receivedBooks = new ArrayList<>();

        Observer observer = new Observer() {
            @Override
            public void update(Book book) {
                receivedBooks.add(book);
            }
        };

        subject.attach(observer);
        assertEquals(1, subject.getObserverCollection().size());

        Book book1 = new Book("Design Patterns");
        subject.add(book1);

        assertEquals(1, receivedBooks.size());
        assertEquals("Design Patterns", receivedBooks.get(0).getTitle());
        assertEquals("Design Patterns", subject.getLatestBook().getTitle());
        assertEquals(1, subject.getBooks().size());

        subject.detach(observer);
        assertEquals(0, subject.getObserverCollection().size());

        Book book2 = new Book("Refactoring");
        subject.add(book2);
        // Observer was detached, so receivedBooks count shouldn't change
        assertEquals(1, receivedBooks.size());
    }

    @Test
    void testSseObserverWithEmitter() {
        SseEmitter emitter = new SseEmitter(0L);
        SseObserver sseObserver = new SseObserver(emitter);
        assertSame(emitter, sseObserver.getEmitter());

        Book book = new Book("Sse Book");
        assertDoesNotThrow(() -> sseObserver.update(book));
    }

    @Test
    void testBooksSseControllerEndpoint() {
        ResponseBodyEmitter emitter = booksSseController.getBooksSse();
        assertNotNull(emitter);
        assertTrue(emitter instanceof SseEmitter);
    }

    @Test
    @Transactional
    void testBooksControllerNotifiesObserversOnCreate() {
        List<Book> captured = new ArrayList<>();
        Observer testObserver = new Observer() {
            @Override
            public void update(Book book) {
                captured.add(book);
            }
        };

        allBooksSubject.attach(testObserver);
        try {
            Book book = new Book("Observable Book");
            booksController.create(book);

            assertFalse(captured.isEmpty());
            assertEquals("Observable Book", captured.get(captured.size() - 1).getTitle());
        } finally {
            allBooksSubject.detach(testObserver);
        }
    }

    @Test
    @Transactional
    void testNewBookRequestMethod() {
        List<Book> captured = new ArrayList<>();
        Observer testObserver = new Observer() {
            @Override
            public void update(Book book) {
                captured.add(book);
            }
        };

        allBooksSubject.attach(testObserver);
        try {
            NewBookRequest request = new NewBookRequest("New Book via Request");
            String result = booksController.newBook(request);

            assertTrue(result.contains("Book saved"));
            assertTrue(result.contains("New Book via Request"));
            assertFalse(captured.isEmpty());
            assertEquals("New Book via Request", captured.get(captured.size() - 1).getTitle());
        } finally {
            allBooksSubject.detach(testObserver);
        }
    }

    @Test
    void testMockMvcSseController() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(booksSseController).build();

        mockMvc.perform(get("/books-sse"))
                .andExpect(status().isOk());
    }
}
