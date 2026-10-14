package org.sp.bookish.entity;

import org.sp.bookish.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class TableOfContents implements Element {
    private String something;
    private List<String> entries = new ArrayList<>();

    public TableOfContents() {
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
