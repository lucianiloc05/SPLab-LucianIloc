package org.sp.bookish.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import org.sp.bookish.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TableOfContents extends BaseElement {
    private String something;

    @ElementCollection
    private List<String> entries = new ArrayList<>();

    public TableOfContents() {
        super();
    }

    public TableOfContents(String something) {
        this.something = something;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }

    public List<String> getEntries() {
        return entries;
    }

    public void setEntries(List<String> entries) {
        this.entries = entries != null ? entries : new ArrayList<>();
    }

    public void addEntry(String entry) {
        this.entries.add(entry);
    }

    public void addEntry(String title, int page) {
        addEntry(title + " .......... page " + page);
    }

    @Override
    public void print() {
        if (entries != null && !entries.isEmpty()) {
            System.out.println("Table of Contents:");
            for (String entry : entries) {
                System.out.println(entry);
            }
        } else {
            System.out.println("TableOfContents: " + (something != null ? something : ""));
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
