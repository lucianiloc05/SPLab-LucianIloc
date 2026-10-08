package org.sp.bookish.entity;

public class ImageProxy implements Picture {
    private String url;
    private Dimension dim;
    private Image realImg;

    public ImageProxy() {
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
    public PictureContent getContent() {
        return content();
    }

    public Image getRealImg() {
        return realImg;
    }

    public void setRealImg(Image realImg) {
        this.realImg = realImg;
    }

    public Image getRealImage() {
        return realImg;
    }

    public void setRealImage(Image realImage) {
        this.realImg = realImage;
    }
}
