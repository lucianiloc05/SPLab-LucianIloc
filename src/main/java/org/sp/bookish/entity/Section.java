package org.sp.bookish.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.sp.bookish.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Section extends BaseElement {
    private String title;

    @OneToMany(targetEntity = BaseElement.class, cascade = CascadeType.ALL)
    private List<Element> children = new ArrayList<>();

    public Section() {
        super();
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
