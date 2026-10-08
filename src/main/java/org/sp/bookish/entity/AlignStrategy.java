package org.sp.bookish.entity;

public interface AlignStrategy {
    void render(Paragraph paragraph, Context context);

    default void render(Paragraph paragraph) {
        render(paragraph, null);
    }
}
