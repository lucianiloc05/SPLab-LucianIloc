package org.sp.bookish.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.sp.bookish.strategy.AlignStrategy;
import org.sp.bookish.strategy.Context;
import org.sp.bookish.visitor.Visitor;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Paragraph extends BaseElement {

    @Column(length = 2000)
    private String text;

    @Transient
    private AlignStrategy textAlignment;

    public Paragraph() {
        super();
    }

    public Paragraph(String text) {
        this.text = text;
    }

    public Paragraph(String text, AlignStrategy textAlignment) {
        this.text = text;
        this.textAlignment = textAlignment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonIgnore
    public AlignStrategy getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(AlignStrategy textAlignment) {
        this.textAlignment = textAlignment;
    }

    @JsonIgnore
    public AlignStrategy getAlignStrategy() {
        return textAlignment;
    }

    public void setAlignStrategy(AlignStrategy alignStrategy) {
        this.textAlignment = alignStrategy;
    }

    @Override
    public void print() {
        if (textAlignment != null) {
            textAlignment.render(this, null);
        } else {
            System.out.println("Paragraph: " + text);
        }
    }

    public void print(Context context) {
        if (textAlignment != null) {
            textAlignment.render(this, context);
        } else {
            System.out.println("Paragraph: " + text);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
