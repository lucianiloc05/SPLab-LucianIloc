package org.sp.bookish.commands;

import org.sp.bookish.entity.Book;
import org.sp.bookish.persistence.BooksRepository;
import org.sp.bookish.persistence.BooksRepositoryAdapter;
import org.sp.bookish.persistence.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandContext {

    private CrudRepository<Book, Integer> booksRepository;

    public CommandContext() {
    }

    @Autowired
    public CommandContext(CrudRepository<Book, Integer> booksRepository) {
        this.booksRepository = booksRepository;
    }

    public CommandContext(BooksRepository booksRepository) {
        this.booksRepository = new BooksRepositoryAdapter(booksRepository);
    }

    public CrudRepository<Book, Integer> getBooksRepository() {
        return booksRepository;
    }

    public void setBooksRepository(CrudRepository<Book, Integer> booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void setBooksRepository(BooksRepository booksRepository) {
        this.booksRepository = new BooksRepositoryAdapter(booksRepository);
    }
}
