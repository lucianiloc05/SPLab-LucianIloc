package org.sp.bookish.entity;

public interface Picture extends Element {
    String url();
    Dimension dim();
    PictureContent content();

    default String getUrl() {
        return url();
    }

    default Dimension getDim() {
        return dim();
    }

    default PictureContent getContent() {
        return content();
    }
}
