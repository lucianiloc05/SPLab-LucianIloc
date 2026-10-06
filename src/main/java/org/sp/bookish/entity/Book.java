package org.sp.bookish.entity;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private List<Author> authors = new ArrayList<>();
    private List<Element> elements = new ArrayList<>();

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, List<Author> authors, List<Element> elements) {
        this.title = title;
        this.authors = authors != null ? authors : new ArrayList<>();
        this.elements = elements != null ? elements : new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void add(Element element) {
        this.elements.add(element);
    }

    public void addContent(Element element) {
        this.elements.add(element);
    }

    public void remove(Element element) {
        this.elements.remove(element);
    }

    public Element get(int index) {
        return this.elements.get(index);
    }

    public void print() {
        System.out.println("Book: " + title);
        System.out.println("Authors:");
        for (Author author : authors) {
            author.print();
        }
        for (Element element : elements) {
            element.print();
        }
    }
}
