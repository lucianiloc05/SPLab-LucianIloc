package org.sp.bookish.entity;

import org.sp.bookish.visitor.Visitor;

public interface Element {
    void print();
    void accept(Visitor visitor);

    default void add(Element element) {
        throw new UnsupportedOperationException("add() is not supported on this element");
    }

    default void remove(Element element) {
        throw new UnsupportedOperationException("remove() is not supported on this element");
    }

    default Element get(int index) {
        throw new UnsupportedOperationException("get() is not supported on this element");
    }
}
