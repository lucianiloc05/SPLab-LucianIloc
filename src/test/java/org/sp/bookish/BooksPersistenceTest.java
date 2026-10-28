package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.entity.*;
import org.sp.bookish.persistence.AuthorsRepository;
import org.sp.bookish.persistence.BooksRepository;
import org.sp.bookish.persistence.BooksRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BooksPersistenceTest {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private BooksRepositoryAdapter booksRepositoryAdapter;

    @Test
    @Transactional
    void testSaveAndFindBook() {
        Book book = new Book("Persistence in Action");
        Author author = new Author("Martin", "Fowler");
        book.addAuthor(author);

        Section section = new Section("Chapter 1: Storage");
        section.add(new Paragraph("Relational databases store tabular data."));
        section.add(new Table("Storage Engines"));
        section.add(new ImageProxy("architecture.png", new Dimension(1024, 768)));

        book.add(section);

        Book savedBook = booksRepository.save(book);
        assertNotNull(savedBook.getId());
        assertEquals("Persistence in Action", savedBook.getTitle());
        assertEquals(1, savedBook.getAuthors().size());
        assertEquals(1, savedBook.getChildren().size());

        Optional<Book> foundBookOpt = booksRepository.findById(savedBook.getId());
        assertTrue(foundBookOpt.isPresent());
        Book foundBook = foundBookOpt.get();
        assertEquals("Persistence in Action", foundBook.getTitle());
        assertEquals(1, foundBook.getAuthors().size());
        assertEquals("Martin", foundBook.getAuthors().get(0).getName());
        assertEquals(1, foundBook.getChildren().size());

        Section foundSection = (Section) foundBook.getChildren().get(0);
        assertEquals(3, foundSection.getChildren().size());
    }

    @Test
    @Transactional
    void testAuthorPersistence() {
        Author author = new Author("Joshua", "Bloch");
        Author savedAuthor = authorsRepository.save(author);

        assertNotNull(savedAuthor.getId());
        Optional<Author> found = authorsRepository.findById(savedAuthor.getId());
        assertTrue(found.isPresent());
        assertEquals("Joshua", found.get().getName());
        assertEquals("Bloch", found.get().getSurname());
    }

    @Test
    @Transactional
    void testBooksRepositoryAdapter() {
        Book book = new Book("Adapter Pattern Guide");
        Book saved = booksRepositoryAdapter.save(book);
        assertNotNull(saved.getId());

        assertTrue(booksRepositoryAdapter.existsById(saved.getId()));
        Optional<Book> found = booksRepositoryAdapter.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Adapter Pattern Guide", found.get().getTitle());

        booksRepositoryAdapter.deleteById(saved.getId());
        assertFalse(booksRepositoryAdapter.existsById(saved.getId()));
    }
}
