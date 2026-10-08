package org.sp.bookish.entity;

public class AlignLeft implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Context context) {
        System.out.println("Align Left: " + (paragraph != null && paragraph.getText() != null ? paragraph.getText() : ""));
    }
}
