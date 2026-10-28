package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.commands.*;
import org.sp.bookish.controllers.BooksController;
import org.sp.bookish.entity.Author;
import org.sp.bookish.entity.Book;
import org.sp.bookish.entity.Paragraph;
import org.sp.bookish.persistence.BooksRepository;
import org.sp.bookish.persistence.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BooksControllerTest {

    @Autowired
    private BooksController booksController;

    @Autowired
    private CrudRepository<Book, Integer> booksRepository;

    @Autowired
    private BooksRepository springBooksRepository;

    @Autowired
    private CommandContext commandContext;

    @Test
    @Transactional
    void testDirectControllerOperations() {
        Book book = new Book("Direct Controller Book");
        book.addAuthor(new Author("Ada", "Lovelace"));
        book.add(new Paragraph("First algorithm"));

        ResponseEntity<Book> createResponse = booksController.create(book);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        Integer bookId = createResponse.getBody().getId();
        assertNotNull(bookId);

        ResponseEntity<Book> getResponse = booksController.getById(bookId);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Direct Controller Book", getResponse.getBody().getTitle());

        Book updatedInfo = new Book("Updated Title");
        ResponseEntity<Book> updateResponse = booksController.update(bookId, updatedInfo);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Updated Title", updateResponse.getBody().getTitle());

        ResponseEntity<List<Book>> allResponse = booksController.getAll();
        assertEquals(HttpStatus.OK, allResponse.getStatusCode());
        assertTrue(allResponse.getBody().stream().anyMatch(b -> b.getId().equals(bookId)));

        ResponseEntity<Void> deleteResponse = booksController.delete(bookId);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        ResponseEntity<Book> notFoundResponse = booksController.getById(bookId);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
    }

    @Test
    @Transactional
    void testCommandsDirectly() {
        CommandContext context = new CommandContext(booksRepository);

        Book book = new Book("Command Pattern Book");
        CreateBookCommand createCmd = new CreateBookCommand(context, book);
        Book created = createCmd.execute();
        assertNotNull(created.getId());

        GetBookByIdCommand getCmd = new GetBookByIdCommand(context, created.getId());
        assertTrue(getCmd.execute().isPresent());

        GetAllBooksCommand getAllCmd = new GetAllBooksCommand(context);
        assertTrue(getAllCmd.execute().stream().anyMatch(b -> b.getId().equals(created.getId())));

        UpdateBookCommand updateCmd = new UpdateBookCommand(context, created.getId(), new Book("New Title"));
        assertTrue(updateCmd.execute().isPresent());
        assertEquals("New Title", updateCmd.execute().get().getTitle());

        DeleteBookCommand deleteCmd = new DeleteBookCommand(context, created.getId());
        deleteCmd.execute();
        assertFalse(context.getBooksRepository().existsById(created.getId()));
    }

    @Test
    @Transactional
    void testMockMvcEndpoints() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();

        String bookJson = "{\"title\":\"MockMvc Book\"}";

        String postResult = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("MockMvc Book"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(postResult.contains("\"title\":\"MockMvc Book\""));

        // Retrieve created book id directly from repository
        Book createdBook = booksRepository.findAll().stream()
                .filter(b -> "MockMvc Book".equals(b.getTitle()))
                .findFirst()
                .orElseThrow();
        Integer id = createdBook.getId();

        mockMvc.perform(get("/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("MockMvc Book"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        String updateData = "{\"title\":\"Updated MockMvc Title\"}";
        mockMvc.perform(put("/books/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated MockMvc Title"));

        mockMvc.perform(delete("/books/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/books/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testControllerWithSpringRepositoryConstructor() {
        BooksController controllerWithSpringRepo = new BooksController(springBooksRepository);
        assertNotNull(controllerWithSpringRepo.getBooksRepository());
        assertNotNull(controllerWithSpringRepo.getCommandContext());
    }
}
