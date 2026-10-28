package org.sp.bookish.commands;

public class DeleteBookCommand implements Command<Void> {

    private final CommandContext context;
    private final Integer id;

    public DeleteBookCommand(CommandContext context, Integer id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public Void execute() {
        context.getBooksRepository().deleteById(id);
        return null;
    }

    public Integer getId() {
        return id;
    }
}
