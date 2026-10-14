package org.sp.bookish.visitor;

import org.sp.bookish.entity.*;

import java.util.ArrayList;
import java.util.List;

public class GenerateToC implements Visitor {
    private TableOfContents toc = new TableOfContents();
    private List<String> entries = new ArrayList<>();
    private int currentPage = 1;

    public GenerateToC() {
    }

    public GenerateToC(int startingPage) {
        this.currentPage = startingPage;
    }

    @Override
    public void visit(Book book) {
        for (Element child : book.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Section section) {
        if (section.getTitle() != null) {
            String entry = section.getTitle() + " .......... page " + currentPage;
            entries.add(entry);
            toc.addEntry(entry);
        }
        for (Element child : section.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(TableOfContents tableOfContents) {
        currentPage++;
    }

    @Override
    public void visit(Paragraph paragraph) {
        currentPage++;
    }

    @Override
    public void visit(ImageProxy imageProxy) {
        currentPage++;
    }

    @Override
    public void visit(Image image) {
        currentPage++;
    }

    @Override
    public void visit(Table table) {
        currentPage++;
    }

    public TableOfContents getToC() {
        return toc;
    }

    public List<String> getEntries() {
        return entries;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void reset() {
        this.toc = new TableOfContents();
        this.entries.clear();
        this.currentPage = 1;
    }
}
