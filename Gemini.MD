# Bookish - Design Patterns & Clean Architecture

## 1. Project Overview
**Bookish** is a modular document and book management platform inspired by cloud storage services (like Google Drive). The goal of the project is to build an extensible, maintainable system applying **Design Patterns**, **SOLID principles**, and **Clean Code** practices.

The initial milestones focus on the core domain model for representing documents hierarchically and formatting text dynamically.

---

## 2. Architectural Design

### 2.1 The Composite Pattern
The document structure follows the Gang of Four (GoF) **Composite Pattern** (transparent variant). This allows clients to treat individual content elements and compositions of elements uniformly.

### 2.2 The Strategy Pattern
Text alignment follows the GoF **Strategy Pattern**. A `Paragraph` delegates its rendering to an `AlignStrategy` (`AlignLeft`, `AlignRight`, or `AlignCenter`), with an optional `Context` (representing parameters such as characters per page).

```mermaid
classDiagram
    direction TB

    class Element {
        <<interface>>
        +print() void
        +add(Element element) void
        +remove(Element element) void
        +get(int index) Element
    }

    class Book {
        -String title
        -List~Author~ authors
        -List~Element~ elements
        +addAuthor(Author author) void
        +add(Element element) void
        +remove(Element element) void
        +get(int index) Element
        +print() void
    }

    class Author {
        -String name
        -String surname
        +print() void
    }

    class Section {
        -String title
        -List~Element~ children
        +add(Element element) void
        +remove(Element element) void
        +get(int index) Element
        +print() void
    }

    class TableOfContents {
        -String something
        +print() void
    }

    class Paragraph {
        -String text
        -AlignStrategy textAlignment
        +setTextAlignment(AlignStrategy textAlignment) void
        +getTextAlignment() AlignStrategy
        +setAlignStrategy(AlignStrategy alignStrategy) void
        +getAlignStrategy() AlignStrategy
        +print() void
        +print(Context context) void
    }

    class Image {
        -String url
        +print() void
    }

    class Table {
        -String something
        +print() void
    }

    class Context {
        -int charactersPerPage
        +getCharactersPerPage() int
        +setCharactersPerPage(int charactersPerPage) void
    }

    class AlignStrategy {
        <<interface>>
        +render(Paragraph paragraph, Context context) void
        +render(Paragraph paragraph) void
    }

    class AlignLeft {
        +render(Paragraph paragraph, Context context) void
    }

    class AlignRight {
        +render(Paragraph paragraph, Context context) void
    }

    class AlignCenter {
        +render(Paragraph paragraph, Context context) void
    }

    Book *-- "1..*" Element : elements
    Book o-- "0..*" Author : authors
    Element <|.. Section : implements
    Element <|.. TableOfContents : implements
    Element <|.. Paragraph : implements
    Element <|.. Image : implements
    Element <|.. Table : implements
    Section o-- "1..*" Element : children
    Paragraph o-- "0..1" AlignStrategy : textAlignment
    AlignStrategy <|.. AlignLeft : implements
    AlignStrategy <|.. AlignRight : implements
    AlignStrategy <|.. AlignCenter : implements
```

---

## 3. Package & Class Structure

All domain entities reside in package: `org.sp.bookish.entity`

| Class / Interface | Role in Pattern | Description |
| :--- | :--- | :--- |
| [`Element`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Element.java) | **Component** | Declares the common interface for all components in the document tree (`print()`, `add()`, `remove()`, `get()`). Default methods throw `UnsupportedOperationException` for leaf elements. |
| [`Section`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Section.java) | **Composite** | Represents a structured section / chapter with a title and a list of child `Element` objects. Propagates operations like `print()` recursively to its children. |
| [`Paragraph`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Paragraph.java) | **Leaf & Context** | Holds text content and an optional `AlignStrategy` reference (`textAlignment`). Delegates rendering to strategy if present. |
| [`Image`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Image.java) | **Leaf** | Holds an image URL or resource path. |
| [`Table`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Table.java) | **Leaf** | Represents tabular data or placeholder content. |
| [`TableOfContents`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/TableOfContents.java) | **Leaf** | Represents the book / document table of contents. |
| [`Book`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Book.java) | **Root Entity** | Contains the book's title, authors, and root-level `Element` contents. |
| [`Author`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Author.java) | **Entity** | Holds author identity (`name`, `surname`) and a `print()` method. |
| [`AlignStrategy`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/AlignStrategy.java) | **Strategy Interface** | Defines the contract for alignment rendering: `render(Paragraph, Context)`. |
| [`AlignLeft`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/AlignLeft.java) | **Concrete Strategy** | Left alignment rendering strategy. |
| [`AlignRight`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/AlignRight.java) | **Concrete Strategy** | Right alignment rendering strategy. |
| [`AlignCenter`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/AlignCenter.java) | **Concrete Strategy** | Center alignment rendering strategy. |
| [`Context`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Context.java) | **Strategy Context** | Context object holding configuration such as `charactersPerPage`. |

---

## 4. Design Decisions

1. **Simplicity First (No SQL / JPA for now)**:
   - Domain models are pure Java POJOs, keeping them easily testable and decoupled from persistence mechanisms.
2. **Transparent Composite**:
   - `Element` interface provides default implementations for `add`, `remove`, and `get` that throw `UnsupportedOperationException`. Leaf classes don't need redundant stub implementations, while `Section` overrides them.
3. **Strategy Pattern for Alignment**:
   - Encapsulates text alignment algorithms away from `Paragraph`.
   - Strategies can be interchanged dynamically at runtime using `setTextAlignment` / `setAlignStrategy`.
4. **Encapsulation & Extensibility**:
   - Standard getters, setters, and constructors are supplied for easy instantiation and serialization.

---

## 5. Usage Example

```java
Author author = new Author("Robert", "Martin");
Book noSqlBook = new Book("Clean Code");
noSqlBook.addAuthor(author);

Section cap1 = new Section("Chapter 1: Clean Code");

Paragraph p1 = new Paragraph("Writing clean code is what you must do...");
p1.setTextAlignment(new AlignLeft());

Paragraph p2 = new Paragraph("Names should reveal intent.");
p2.setTextAlignment(new AlignCenter());

Paragraph p3 = new Paragraph("Functions should do one thing.");
p3.setTextAlignment(new AlignRight());

cap1.add(p1);
cap1.add(p2);
cap1.add(p3);

noSqlBook.add(cap1);
noSqlBook.print();
```

---

## 6. Future Roadmap

- **Visitor Pattern**: For document serialization, statistics (word count, image count), and rendering.
- **Proxy Pattern**: Lazy loading of large images or remote assets (`ImageProxy`).
- **Persistence (JPA/Hibernate)**: When persistence is ready, mapping the composite hierarchy using JPA entities with inheritance strategies.
- **REST Controller & Drive Services**: Uploading, organizing, and versioning documents in a Google Drive-like API.
