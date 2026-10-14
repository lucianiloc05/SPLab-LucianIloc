package org.sp.bookish.entity;

import org.sp.bookish.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Book extends Section {
    private List<Author> authors = new ArrayList<>();

    public Book() {
        super();
    }

    public Book(String title) {
        super(title);
    }

    public Book(String title, List<Author> authors, List<Element> elements) {
        super(title, elements);
        this.authors = authors != null ? authors : new ArrayList<>();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Element> getElements() {
        return getChildren();
    }

    public void setElements(List<Element> elements) {
        setChildren(elements);
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addContent(Element element) {
        add(element);
    }

    @Override
    public void print() {
        System.out.println("Book: " + getTitle());
        System.out.println("Authors:");
        for (Author author : authors) {
            author.print();
        }
        for (Element element : getChildren()) {
            element.print();
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
