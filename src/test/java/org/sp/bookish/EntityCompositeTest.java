package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.entity.*;

import static org.junit.jupiter.api.Assertions.*;

class EntityCompositeTest {

    @Test
    void testCompositeStructure() {
        Book book = new Book("Design Patterns Explained");
        Author author = new Author("John", "Doe");
        book.addAuthor(author);

        Section chapter1 = new Section("Chapter 1: Introduction");
        Paragraph p1 = new Paragraph("This is the introductory paragraph.");
        Image img1 = new Image("diagram.png");
        Table tbl1 = new Table("Table of Figures");
        TableOfContents toc = new TableOfContents("ToC Content");

        chapter1.add(p1);
        chapter1.add(img1);
        chapter1.add(tbl1);
        chapter1.add(toc);

        Section subSection = new Section("1.1 Sub-topics");
        subSection.add(new Paragraph("Details of subtopic."));
        chapter1.add(subSection);

        book.add(chapter1);

        assertEquals(1, book.getElements().size());
        assertEquals(1, book.getAuthors().size());
        assertEquals(5, chapter1.getChildren().size());
        assertSame(p1, chapter1.get(0));

        // Leaf elements should reject add/remove/get operations
        assertThrows(UnsupportedOperationException.class, () -> p1.add(new Paragraph("nested")));
        assertThrows(UnsupportedOperationException.class, () -> img1.get(0));

        // Ensure print does not throw exceptions
        assertDoesNotThrow(book::print);
    }
}
