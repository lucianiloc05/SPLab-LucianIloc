package org.sp.bookish.commands;

import org.sp.bookish.entity.Book;

import java.util.Optional;

public class UpdateBookCommand implements Command<Optional<Book>> {

    private final CommandContext context;
    private final Integer id;
    private final Book book;

    public UpdateBookCommand(CommandContext context, Integer id, Book book) {
        this.context = context;
        this.id = id;
        this.book = book;
    }

    @Override
    public Optional<Book> execute() {
        Optional<Book> existing = context.getBooksRepository().findById(id);
        if (existing.isPresent()) {
            if (book != null) {
                book.setId(id);
                return Optional.of(context.getBooksRepository().save(book));
            }
        }
        return Optional.empty();
    }

    public Integer getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }
}
