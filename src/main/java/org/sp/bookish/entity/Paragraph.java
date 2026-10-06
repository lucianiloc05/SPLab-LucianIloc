package org.sp.bookish.entity;

public class Paragraph implements Element {
    private String text;
    private AlignStrategy textAlignment;

    public Paragraph() {
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

    public AlignStrategy getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(AlignStrategy textAlignment) {
        this.textAlignment = textAlignment;
    }

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
}
