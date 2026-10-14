package org.sp.bookish.strategy;

import org.sp.bookish.entity.Paragraph;

public class AlignLeft implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Context context) {
        System.out.println("Align Left: " + paragraph.getText());
    }
}
