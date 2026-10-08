package org.sp.bookish;

import org.sp.bookish.entity.*;
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

            System.out.println("======================================");
        };
    }
}
