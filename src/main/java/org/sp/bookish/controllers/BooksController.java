package org.sp.bookish.controllers;

import org.sp.bookish.commands.*;
import org.sp.bookish.entity.Book;
import org.sp.bookish.persistence.BooksRepository;
import org.sp.bookish.persistence.BooksRepositoryAdapter;
import org.sp.bookish.persistence.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final CrudRepository<Book, Integer> booksRepository;
    private final CommandContext commandContext;

    @Autowired
    public BooksController(CrudRepository<Book, Integer> booksRepository, @Autowired(required = false) CommandContext commandContext) {
        this.booksRepository = booksRepository;
        if (commandContext != null) {
            this.commandContext = commandContext;
            this.commandContext.setBooksRepository(booksRepository);
        } else {
            this.commandContext = new CommandContext(booksRepository);
        }
    }

    public BooksController(BooksRepository booksRepository) {
        this(new BooksRepositoryAdapter(booksRepository), null);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = new GetAllBooksCommand(commandContext).execute();
        return ResponseEntity.ok(books);
    }

    public ResponseEntity<List<Book>> getAllBooks() {
        return getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Integer id) {
        Optional<Book> book = new GetBookByIdCommand(commandContext, id).execute();
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Book> getBookById(Integer id) {
        return getById(id);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody(required = false) Book book) {
        Book savedBook = new CreateBookCommand(commandContext, book).execute();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    public ResponseEntity<Book> createBook(Book book) {
        return create(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book book) {
        Optional<Book> updated = new UpdateBookCommand(commandContext, id, book).execute();
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Book> updateBook(Integer id, Book book) {
        return update(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!booksRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        new DeleteBookCommand(commandContext, id).execute();
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteBook(Integer id) {
        return delete(id);
    }

    public CommandContext getCommandContext() {
        return commandContext;
    }

    public CrudRepository<Book, Integer> getBooksRepository() {
        return booksRepository;
    }
}
