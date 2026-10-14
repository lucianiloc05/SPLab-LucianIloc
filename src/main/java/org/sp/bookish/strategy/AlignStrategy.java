package org.sp.bookish.strategy;

import org.sp.bookish.entity.Paragraph;

public interface AlignStrategy {
    void render(Paragraph paragraph, Context context);

    default void render(Paragraph paragraph) {
        render(paragraph, null);
    }
}
