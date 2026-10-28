package org.sp.bookish.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.sp.bookish.visitor.Visitor;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImageProxy extends BaseElement implements Picture {
    private String url;

    @Embedded
    private Dimension dim;

    @Transient
    private Image realImg;

    public ImageProxy() {
        super();
    }

    public ImageProxy(String url) {
        this.url = url;
    }

    public ImageProxy(String url, Dimension dim) {
        this.url = url;
        this.dim = dim;
    }

    public Image loadImage() {
        if (realImg == null) {
            realImg = new Image(url, dim);
        }
        return realImg;
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
    public PictureContent content() {
        return loadImage().content();
    }

    @Override
    public void print() {
        loadImage().print();
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
    @JsonIgnore
    public PictureContent getContent() {
        return content();
    }

    @JsonIgnore
    public Image getRealImg() {
        return realImg;
    }

    public void setRealImg(Image realImg) {
        this.realImg = realImg;
    }

    @JsonIgnore
    public Image getRealImage() {
        return realImg;
    }

    public void setRealImage(Image realImage) {
        this.realImg = realImage;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
