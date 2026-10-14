package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.entity.*;
import org.sp.bookish.visitor.*;

import static org.junit.jupiter.api.Assertions.*;

class VisitorPatternTest {

    @Test
    void testBookStatistics() {
        Book book = new Book("Design Patterns in Practice");
        Section chapter1 = new Section("Chapter 1: Creational Patterns");
        chapter1.add(new Paragraph("Intro to creational patterns."));
        chapter1.add(new Paragraph("Factory pattern discussion."));
        chapter1.add(new ImageProxy("factory_diagram.png"));
        chapter1.add(new Table("Comparison Table"));

        Section subSection = new Section("1.1 Singleton");
        subSection.add(new Paragraph("Singleton details."));
        subSection.add(new Image("singleton_structure.png"));
        chapter1.add(subSection);

        Section chapter2 = new Section("Chapter 2: Structural Patterns");
        chapter2.add(new Paragraph("Intro to structural patterns."));
        chapter2.add(new Table("Structural Comparison"));

        book.add(chapter1);
        book.add(chapter2);

        BookStatistics stats = new BookStatistics();
        book.accept(stats);

        assertEquals(4, stats.getNumberOfParagraphs());
        assertEquals(2, stats.getNumberOfImages());
        assertEquals(2, stats.getNumberOfTables());
        assertEquals(1, stats.getNumberOfBooks());
        assertEquals(3, stats.getNumberOfSections());

        assertDoesNotThrow(stats::printStatistics);
    }

    @Test
    void testGenerateToC() {
        Book book = new Book("Clean Architecture");
        Section part1 = new Section("Part 1: Introduction");
        part1.add(new Paragraph("Architecture overview."));

        Section part2 = new Section("Part 2: Starting with the Components");
        Section ch3 = new Section("Chapter 3: Component Cohesion");
        ch3.add(new Paragraph("Details about cohesion."));
        part2.add(ch3);

        book.add(part1);
        book.add(part2);

        GenerateToC generator = new GenerateToC();
        book.accept(generator);

        TableOfContents toc = generator.getToC();
        assertNotNull(toc);
        assertEquals(3, toc.getEntries().size());
        assertEquals(3, generator.getEntries().size());
        assertTrue(toc.getEntries().get(0).contains("Part 1: Introduction") && toc.getEntries().get(0).contains("page 1"));
        assertTrue(toc.getEntries().get(1).contains("Part 2: Starting with the Components") && toc.getEntries().get(1).contains("page 2"));
        assertTrue(toc.getEntries().get(2).contains("Chapter 3: Component Cohesion") && toc.getEntries().get(2).contains("page 2"));

        assertDoesNotThrow(toc::print);
    }

    @Test
    void testDynamicPageNumberingWhenParagraphsAdded() {
        Book book = new Book("Dynamic Pagination");
        Section s1 = new Section("Section 1");
        s1.add(new Paragraph("Paragraph 1.1"));

        Section s2 = new Section("Section 2");
        s2.add(new Paragraph("Paragraph 2.1"));

        book.add(s1);
        book.add(s2);

        // Initial generation
        GenerateToC gen1 = new GenerateToC();
        book.accept(gen1);
        assertTrue(gen1.getEntries().get(0).contains("page 1"));
        assertTrue(gen1.getEntries().get(1).contains("page 2"));

        // Adding 2 more paragraphs to Section 1 should shift Section 2 from page 2 to page 4
        s1.add(new Paragraph("Paragraph 1.2"));
        s1.add(new Paragraph("Paragraph 1.3"));

        GenerateToC gen2 = new GenerateToC();
        book.accept(gen2);
        assertTrue(gen2.getEntries().get(0).contains("page 1"));
        assertTrue(gen2.getEntries().get(1).contains("page 4"));
    }

    @Test
    void testRenderContentVisitor() {
        Book book = new Book("Domain-Driven Design");
        book.addAuthor(new Author("Eric", "Evans"));

        Section sec1 = new Section("Chapter 1: Ubiquitous Language");
        sec1.add(new Paragraph("The language of the domain."));
        sec1.add(new ImageProxy("domain_model.png"));
        sec1.add(new Table("Entity Matrix"));
        sec1.add(new TableOfContents("ToC"));

        book.add(sec1);

        RenderContentVisitor visitor = new RenderContentVisitor();
        assertDoesNotThrow(() -> book.accept(visitor));
    }

    @Test
    void testIndividualElementAccept() {
        Paragraph paragraph = new Paragraph("Test paragraph");
        Table table = new Table("Test table");
        ImageProxy proxy = new ImageProxy("test_proxy.png");
        Image img = new Image("test_img.png");
        TableOfContents toc = new TableOfContents("Test TOC");

        BookStatistics stats = new BookStatistics();
        paragraph.accept(stats);
        table.accept(stats);
        proxy.accept(stats);
        img.accept(stats);
        toc.accept(stats);

        assertEquals(1, stats.getNumberOfParagraphs());
        assertEquals(1, stats.getNumberOfTables());
        assertEquals(2, stats.getNumberOfImages());
    }
}
