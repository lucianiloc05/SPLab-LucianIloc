package org.sp.bookish.commands;

import org.sp.bookish.entity.Book;

public class CreateBookCommand implements Command<Book> {

    private final CommandContext context;
    private final Book book;

    public CreateBookCommand(CommandContext context) {
        this(context, new Book());
    }

    public CreateBookCommand(CommandContext context, Book book) {
        this.context = context;
        this.book = book != null ? book : new Book();
    }

    @Override
    public Book execute() {
        return context.getBooksRepository().save(book);
    }

    public Book getBook() {
        return book;
    }
}
