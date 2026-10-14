package org.sp.bookish.entity;

public class BookStatistics implements Visitor {
    private int numberOfImages = 0;
    private int numberOfTables = 0;
    private int numberOfParagraphs = 0;
    private int numberOfBooks = 0;
    private int numberOfSections = 0;

    @Override
    public void visit(Book book) {
        numberOfBooks++;
        for (Element child : book.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Section section) {
        numberOfSections++;
        for (Element child : section.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(TableOfContents tableOfContents) {
    }

    @Override
    public void visit(Paragraph paragraph) {
        numberOfParagraphs++;
    }

    @Override
    public void visit(ImageProxy imageProxy) {
        numberOfImages++;
    }

    @Override
    public void visit(Image image) {
        numberOfImages++;
    }

    @Override
    public void visit(Table table) {
        numberOfTables++;
    }

    public void printStatistics() {
        System.out.println("Book Statistics:");
        System.out.println("*** Number of Images: " + numberOfImages);
        System.out.println("*** Number of Tables: " + numberOfTables);
        System.out.println("*** Number of Paragraphs: " + numberOfParagraphs);
    }

    public int getNumberOfImages() {
        return numberOfImages;
    }

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public int getNumberOfParagraphs() {
        return numberOfParagraphs;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public int getImagesCount() {
        return numberOfImages;
    }

    public int getTablesCount() {
        return numberOfTables;
    }

    public int getParagraphsCount() {
        return numberOfParagraphs;
    }
}
