package org.sp.bookish.commands;

public interface Command<T> {
    T execute();
}
