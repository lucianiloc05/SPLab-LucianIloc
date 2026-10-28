package org.sp.bookish.commands;

import org.sp.bookish.entity.Book;

import java.util.Optional;

public class GetBookByIdCommand implements Command<Optional<Book>> {

    private final CommandContext context;
    private final Integer id;

    public GetBookByIdCommand(CommandContext context, Integer id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public Optional<Book> execute() {
        return context.getBooksRepository().findById(id);
    }

    public Integer getId() {
        return id;
    }
}
