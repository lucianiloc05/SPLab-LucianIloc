package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.entity.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StrategyPatternTest {

    @Test
    void testDefaultParagraphPrint() {
        Paragraph paragraph = new Paragraph("Default text");
        assertNull(paragraph.getTextAlignment());
        assertNull(paragraph.getAlignStrategy());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            paragraph.print();
            assertEquals("Paragraph: Default text" + System.lineSeparator(), outContent.toString());
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void testAlignLeftStrategy() {
        Paragraph paragraph = new Paragraph("Left aligned text");
        AlignStrategy alignLeft = new AlignLeft();
        paragraph.setTextAlignment(alignLeft);
        assertSame(alignLeft, paragraph.getTextAlignment());
        assertSame(alignLeft, paragraph.getAlignStrategy());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            paragraph.print();
            assertEquals("Align Left: Left aligned text" + System.lineSeparator(), outContent.toString());
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void testAlignRightStrategy() {
        Paragraph paragraph = new Paragraph("Right aligned text");
        AlignStrategy alignRight = new AlignRight();
        paragraph.setAlignStrategy(alignRight);
        assertSame(alignRight, paragraph.getTextAlignment());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            paragraph.print();
            assertEquals("Align Right: Right aligned text" + System.lineSeparator(), outContent.toString());
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void testAlignCenterStrategy() {
        Paragraph paragraph = new Paragraph("Center aligned text", new AlignCenter());
        assertTrue(paragraph.getTextAlignment() instanceof AlignCenter);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            paragraph.print();
            assertEquals("Align Center: Center aligned text" + System.lineSeparator(), outContent.toString());
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void testContextAndDynamicStrategySwitching() {
        Context context = new Context(80);
        assertEquals(80, context.getCharactersPerPage());
        context.setCharactersPerPage(120);
        assertEquals(120, context.getCharactersPerPage());

        Paragraph paragraph = new Paragraph("Dynamic text");
        paragraph.setTextAlignment(new AlignLeft());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            paragraph.print(context);
            paragraph.setTextAlignment(new AlignCenter());
            paragraph.print(context);
            paragraph.setTextAlignment(new AlignRight());
            paragraph.print(context);

            String expected = "Align Left: Dynamic text" + System.lineSeparator()
                    + "Align Center: Dynamic text" + System.lineSeparator()
                    + "Align Right: Dynamic text" + System.lineSeparator();
            assertEquals(expected, outContent.toString());
        } finally {
            System.setOut(System.out);
        }
    }
}
