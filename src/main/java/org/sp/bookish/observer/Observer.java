package org.sp.bookish.observer;

import org.sp.bookish.entity.Book;

public interface Observer {
    void update(Book book);

    default void update() {
        update(null);
    }
}
