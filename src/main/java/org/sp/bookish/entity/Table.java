package org.sp.bookish.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import org.sp.bookish.visitor.Visitor;

@Entity
@jakarta.persistence.Table(name = "book_tables")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Table extends BaseElement {
    private String something;

    public Table() {
        super();
    }

    public Table(String something) {
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
        System.out.println("Table: " + something);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
