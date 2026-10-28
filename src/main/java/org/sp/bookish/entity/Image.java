package org.sp.bookish.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.sp.bookish.visitor.Visitor;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Image extends BaseElement implements Picture {
    private String url;

    @Embedded
    private Dimension dim;

    @Transient
    private ImageContent content;

    public Image() {
        super();
    }

    public Image(String url) {
        this.url = url;
        this.content = new ImageContent();
    }

    public Image(String url, Dimension dim) {
        this.url = url;
        this.dim = dim;
        this.content = new ImageContent();
    }

    public Image(String url, Dimension dim, ImageContent content) {
        this.url = url;
        this.dim = dim;
        this.content = content;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public Dimension dim() {
        return dim;
    }

    @Override
    public ImageContent content() {
        return content;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Dimension getDim() {
        return dim;
    }

    public void setDim(Dimension dim) {
        this.dim = dim;
    }

    @Override
    public ImageContent getContent() {
        return content;
    }

    public void setContent(ImageContent content) {
        this.content = content;
    }

    @Override
    public void print() {
        System.out.println("Image with name: " + url);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
