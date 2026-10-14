package org.sp.bookish.entity;

public class RenderContentVisitor implements Visitor {

    @Override
    public void visit(Book book) {
        System.out.println("Book: " + book.getTitle());
        System.out.println("Authors:");
        for (Author author : book.getAuthors()) {
            author.print();
        }
        for (Element child : book.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Section section) {
        System.out.println(section.getTitle());
        for (Element child : section.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(TableOfContents tableOfContents) {
        tableOfContents.print();
    }

    @Override
    public void visit(Paragraph paragraph) {
        paragraph.print();
    }

    @Override
    public void visit(ImageProxy imageProxy) {
        imageProxy.print();
    }

    @Override
    public void visit(Image image) {
        image.print();
    }

    @Override
    public void visit(Table table) {
        table.print();
    }
}
