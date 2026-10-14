package org.sp.bookish.visitor;

import org.sp.bookish.entity.*;

public class GenerateToC implements Visitor {
    private TableOfContents toc = new TableOfContents();

    @Override
    public void visit(Book book) {
        for (Element child : book.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Section section) {
        if (section.getTitle() != null) {
            toc.addEntry(section.getTitle());
        }
        for (Element child : section.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(TableOfContents tableOfContents) {
    }

    @Override
    public void visit(Paragraph paragraph) {
    }

    @Override
    public void visit(ImageProxy imageProxy) {
    }

    @Override
    public void visit(Image image) {
    }

    @Override
    public void visit(Table table) {
    }

    public TableOfContents getToC() {
        return toc;
    }
}
