package org.sp.bookish.entity;

public class TableOfContents implements Element {
    private String something;

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

    @Override
    public void print() {
        System.out.println("TableOfContents: " + (something != null ? something : ""));
    }
}
