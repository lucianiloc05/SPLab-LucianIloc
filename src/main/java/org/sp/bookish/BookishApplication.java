package org.sp.bookish;

import org.sp.bookish.entity.*;
import org.sp.bookish.strategy.*;
import org.sp.bookish.visitor.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookishApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookishApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return args -> {
            System.out.println("========== Running Lab Demo ==========");

            long startTime = System.currentTimeMillis();
            ImageProxy img1 = new ImageProxy("Pamela Anderson");
            ImageProxy img2 = new ImageProxy("Kim Kardashian");
            ImageProxy img3 = new ImageProxy("Kirby Griffin");

            Section playboyS1 = new Section("Front Cover");
            playboyS1.add(img1);

            Section playboyS2 = new Section("Summer Girls");
            playboyS2.add(img2);
            playboyS2.add(img3);

            Book playboy = new Book("Playboy");
            playboy.addContent(playboyS1);
            playboy.addContent(playboyS2);

            long endTime = System.currentTimeMillis();
            System.out.println("Creation of the content took " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            playboyS1.print();
            endTime = System.currentTimeMillis();
            System.out.println("Printing of the section 1 took " + (endTime - startTime) + " milliseconds");

            startTime = System.currentTimeMillis();
            playboyS1.print();
            endTime = System.currentTimeMillis();
            System.out.println("Printing again the section 1 took " + (endTime - startTime) + " milliseconds");

            // Strategy pattern demonstration
            System.out.println("\n--- Strategy Pattern Demo ---");
            Paragraph p1 = new Paragraph("Text aligned to the left");
            p1.setTextAlignment(new AlignLeft());

            Paragraph p2 = new Paragraph("Text aligned to the center");
            p2.setTextAlignment(new AlignCenter());

            Paragraph p3 = new Paragraph("Text aligned to the right");
            p3.setTextAlignment(new AlignRight());

            Section textSection = new Section("Text Formatting Section");
            textSection.add(p1);
            textSection.add(p2);
            textSection.add(p3);
            textSection.print();

            // Visitor pattern demonstration
            System.out.println("\n--- Visitor Pattern Demo ---");
            Section cap1 = new Section("Capitolul 1");
            Paragraph p11 = new Paragraph("Moto capitol");
            cap1.add(p11);
            Paragraph p12 = new Paragraph("Paragraph 1");
            cap1.add(p12);
            Paragraph p13 = new Paragraph("Paragraph 2");
            cap1.add(p13);
            Paragraph p14 = new Paragraph("Paragraph 3");
            cap1.add(p14);
            ImageProxy img11 = new ImageProxy("ImageOne");
            Image img12 = new Image("ImageTwo");
            Paragraph p15 = new Paragraph("Some text");
            Table table1 = new Table("Table 1");
            cap1.add(img11);
            cap1.add(img12);
            cap1.add(p15);
            cap1.add(table1);

            Book book = new Book("My Book");
            book.add(cap1);

            System.out.println("\n1. BookStatistics:");
            BookStatistics stats = new BookStatistics();
            book.accept(stats);
            stats.printStatistics();

            System.out.println("\n2. GenerateToC:");
            GenerateToC generateToC = new GenerateToC();
            book.accept(generateToC);
            TableOfContents toc = generateToC.getToC();
            toc.print();

            System.out.println("\n3. RenderContentVisitor:");
            RenderContentVisitor renderVisitor = new RenderContentVisitor();
            book.accept(renderVisitor);

            System.out.println("======================================");
        };
    }
}
