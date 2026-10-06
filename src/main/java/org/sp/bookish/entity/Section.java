package org.sp.bookish.entity;

import java.util.ArrayList;
import java.util.List;

public class Section implements Element {
    private String title;
    private List<Element> children = new ArrayList<>();

    public Section() {
    }

    public Section(String title) {
        this.title = title;
    }

    public Section(String title, List<Element> children) {
        this.title = title;
        this.children = children != null ? children : new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Element> getChildren() {
        return children;
    }

    public void setChildren(List<Element> children) {
        this.children = children;
    }

    @Override
    public void add(Element element) {
        this.children.add(element);
    }

    @Override
    public void remove(Element element) {
        this.children.remove(element);
    }

    @Override
    public Element get(int index) {
        return this.children.get(index);
    }

    @Override
    public void print() {
        System.out.println(title);
        for (Element child : children) {
            child.print();
        }
    }
}
