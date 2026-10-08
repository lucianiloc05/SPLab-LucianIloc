package org.sp.bookish.entity;

public class AlignRight implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Context context) {
        System.out.println("Align Right: " + (paragraph != null && paragraph.getText() != null ? paragraph.getText() : ""));
    }
}
