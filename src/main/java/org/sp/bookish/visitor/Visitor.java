package org.sp.bookish.visitor;

import org.sp.bookish.entity.*;

public interface Visitor {
    void visit(Book book);
    void visit(Section section);
    void visit(TableOfContents tableOfContents);
    void visit(Paragraph paragraph);
    void visit(ImageProxy imageProxy);
    void visit(Table table);

    default void visit(Image image) {
    }

    default void visit(BaseElement baseElement) {
    }
}
