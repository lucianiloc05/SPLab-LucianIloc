# Bookish - Design Patterns & Clean Architecture

## 1. Project Overview
**Bookish** is a modular document and book management platform inspired by cloud storage services (like Google Drive). The goal of the project is to build an extensible, maintainable system applying **Design Patterns**, **SOLID principles**, and **Clean Code** practices.

The initial milestones focus on the core domain model for representing documents hierarchically, formatting text dynamically, lazily loading heavy media resources, and traversing document elements using the Visitor pattern.

---

## 2. Architectural Design

### 2.1 The Composite Pattern
The document structure follows the Gang of Four (GoF) **Composite Pattern** (transparent variant). This allows clients to treat individual content elements and compositions of elements uniformly.

### 2.2 The Strategy Pattern
Text alignment follows the GoF **Strategy Pattern**. A `Paragraph` delegates its rendering to an `AlignStrategy` (`AlignLeft`, `AlignRight`, or `AlignCenter`), with an optional `Context` (representing parameters such as characters per page).

### 2.3 The Proxy Pattern
Image loading follows the GoF **Proxy Pattern** (Virtual Proxy variant). An `ImageProxy` stands in for an `Image` object implementing the common `Picture` interface. Heavy real image instances are only instantiated lazily on demand when `loadImage()`, `print()`, or `content()` is accessed.

### 2.4 The Visitor Pattern
Document traversal and operations follow the GoF **Visitor Pattern**. Each `Element` declares `accept(Visitor)`, enabling new operations to be added without modifying the element classes:
- **`RenderContentVisitor`**: Traverses the document structure to print and format elements.
- **`BookStatistics`**: Accumulates element counts (paragraphs, images, tables, sections, books) and provides `printStatistics()`.
- **`GenerateToC`**: Extracts section titles into a `TableOfContents` accessible via `getToC()`.

```mermaid
classDiagram
    direction TB

    class Element {
        <<interface>>
        +print() void
        +accept(Visitor visitor) void
        +add(Element element) void
        +remove(Element element) void
        +get(int index) Element
    }

    class Book {
        -List~Author~ authors
        +addAuthor(Author author) void
        +addContent(Element element) void
        +getElements() List~Element~
        +setElements(List~Element~ elements) void
        +print() void
        +accept(Visitor visitor) void
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
        +accept(Visitor visitor) void
    }

    class TableOfContents {
        -String something
        -List~String~ entries
        +addEntry(String entry) void
        +getEntries() List~String~
        +print() void
        +accept(Visitor visitor) void
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
        +accept(Visitor visitor) void
    }

    class Picture {
        <<interface>>
        +url() String
        +dim() Dimension
        +content() PictureContent
    }

    class Image {
        -String url
        -Dimension dim
        -ImageContent content
        +url() String
        +dim() Dimension
        +content() ImageContent
        +print() void
        +accept(Visitor visitor) void
    }

    class ImageProxy {
        -String url
        -Dimension dim
        -Image realImg
        +loadImage() Image
        +url() String
        +dim() Dimension
        +content() PictureContent
        +print() void
        +accept(Visitor visitor) void
    }

    class Table {
        -String something
        +print() void
        +accept(Visitor visitor) void
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

    class Visitor {
        <<interface>>
        +visit(Book book) void
        +visit(Section section) void
        +visit(TableOfContents tableOfContents) void
        +visit(Paragraph paragraph) void
        +visit(ImageProxy imageProxy) void
        +visit(Table table) void
        +visit(Image image) void
    }

    class RenderContentVisitor {
        +visit(Book book) void
        +visit(Section section) void
        +visit(TableOfContents tableOfContents) void
        +visit(Paragraph paragraph) void
        +visit(ImageProxy imageProxy) void
        +visit(Table table) void
        +visit(Image image) void
    }

    class BookStatistics {
        -int numberOfImages
        -int numberOfTables
        -int numberOfParagraphs
        -int numberOfBooks
        -int numberOfSections
        +printStatistics() void
        +getNumberOfImages() int
        +getNumberOfTables() int
        +getNumberOfParagraphs() int
    }

    class GenerateToC {
        -TableOfContents toc
        +getToC() TableOfContents
    }

    Section <|-- Book : extends
    Book o-- "0..*" Author : authors
    Element <|.. Section : implements
    Element <|.. TableOfContents : implements
    Element <|.. Paragraph : implements
    Element <|.. Picture : extends
    Element <|.. Table : implements
    Picture <|.. Image : implements
    Picture <|.. ImageProxy : implements
    ImageProxy *-- "1" Image : realImg
    Section o-- "1..*" Element : children
    Paragraph o-- "0..1" AlignStrategy : textAlignment
    AlignStrategy <|.. AlignLeft : implements
    AlignStrategy <|.. AlignRight : implements
    AlignStrategy <|.. AlignCenter : implements
    Visitor <|.. RenderContentVisitor : implements
    Visitor <|.. BookStatistics : implements
    Visitor <|.. GenerateToC : implements
```

---

## 3. Package & Class Structure

### 3.1 Domain Entity Package (`org.sp.bookish.entity`)

| Class / Interface | Role in Pattern | Description |
| :--- | :--- | :--- |
| [`Element`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Element.java) | **Component** | Declares the common interface for all components in the document tree (`print()`, `accept()`, `add()`, `remove()`, `get()`). Default methods throw `UnsupportedOperationException` for leaf elements. |
| [`Section`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Section.java) | **Composite** | Represents a structured section / chapter with a title and a list of child `Element` objects. Propagates operations recursively to its children. |
| [`Paragraph`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Paragraph.java) | **Leaf & Context** | Holds text content and an optional `AlignStrategy` reference (`textAlignment`). Delegates rendering to strategy if present. |
| [`Picture`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Picture.java) | **Subject Interface** | Common interface extending `Element` for both real images and image proxies (`url()`, `dim()`, `content()`). |
| [`Image`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Image.java) | **Real Subject** | Represents real image resource with url, dimensions, and image content. |
| [`ImageProxy`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/ImageProxy.java) | **Proxy** | Virtual proxy that defers loading of `Image` until needed via `loadImage()`. |
| [`Dimension`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Dimension.java) | **Value Object** | Holds 2D image dimensions (`width`, `height`). |
| [`PictureContent`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/PictureContent.java) | **Content Model** | Base class representing picture content. |
| [`ImageContent`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/ImageContent.java) | **Content Model** | Specific content payload for images. |
| [`Table`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Table.java) | **Leaf** | Represents tabular data or placeholder content. |
| [`TableOfContents`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/TableOfContents.java) | **Leaf** | Represents the book / document table of contents. |
| [`Book`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Book.java) | **Composite & Root Entity** | Extends `Section`, contains the book's title, authors, and root-level `Element` contents. |
| [`Author`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/entity/Author.java) | **Entity** | Holds author identity (`name`, `surname`) and a `print()` method. |

### 3.2 Strategy Package (`org.sp.bookish.strategy`)

| Class / Interface | Role in Pattern | Description |
| :--- | :--- | :--- |
| [`AlignStrategy`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/strategy/AlignStrategy.java) | **Strategy Interface** | Defines the contract for alignment rendering: `render(Paragraph, Context)`. |
| [`AlignLeft`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/strategy/AlignLeft.java) | **Concrete Strategy** | Left alignment rendering strategy. |
| [`AlignRight`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/strategy/AlignRight.java) | **Concrete Strategy** | Right alignment rendering strategy. |
| [`AlignCenter`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/strategy/AlignCenter.java) | **Concrete Strategy** | Center alignment rendering strategy. |
| [`Context`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/strategy/Context.java) | **Strategy Context** | Context object holding configuration such as `charactersPerPage`. |

### 3.3 Visitor Package (`org.sp.bookish.visitor`)

| Class / Interface | Role in Pattern | Description |
| :--- | :--- | :--- |
| [`Visitor`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/visitor/Visitor.java) | **Visitor Interface** | Contract defining `visit(...)` methods for all element types. |
| [`RenderContentVisitor`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/visitor/RenderContentVisitor.java) | **Concrete Visitor** | Traverses elements to render/print them cleanly. |
| [`BookStatistics`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/visitor/BookStatistics.java) | **Concrete Visitor** | Collects statistics (counts of paragraphs, images, tables) and displays them via `printStatistics()`. |
| [`GenerateToC`](file:///D:/Lucian/Java/Bookish/src/main/java/org/sp/bookish/visitor/GenerateToC.java) | **Concrete Visitor** | Extracts section titles with dynamic page numbering (1 page per visited element) and builds a `TableOfContents` accessible via `getToC()`. |

---

## 4. Design Decisions

1. **Simplicity First (No SQL / JPA for now)**:
   - Domain models are pure Java POJOs, keeping them easily testable and decoupled from persistence mechanisms.
2. **Transparent Composite**:
   - `Element` interface provides default implementations for `add`, `remove`, and `get` that throw `UnsupportedOperationException`. Leaf classes don't need redundant stub implementations, while `Section` and `Book` override them.
3. **Strategy Pattern for Alignment**:
   - Encapsulates text alignment algorithms away from `Paragraph`.
   - Strategies can be interchanged dynamically at runtime using `setTextAlignment` / `setAlignStrategy`.
4. **Proxy Pattern for Media**:
   - `ImageProxy` acts as a surrogate for `Image`. It stores lightweight metadata (`url`, `dim`) without immediately instantiating the real image until `loadImage()`, `content()`, or `print()` is requested.
5. **Visitor Pattern for Extensible Operations**:
   - `Element.accept(Visitor)` implements double dispatch, allowing operations (like statistics, rendering, ToC generation) to be defined separately without modifying the core element classes.
6. **Encapsulation & Extensibility**:
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

// Lazy loading image via proxy
ImageProxy imgProxy = new ImageProxy("clean_code_cover.png", new Dimension(800, 600));

cap1.add(p1);
cap1.add(p2);
cap1.add(p3);
cap1.add(imgProxy);

noSqlBook.add(cap1);

// 1. Statistics visitor
BookStatistics stats = new BookStatistics();
noSqlBook.accept(stats);
stats.printStatistics();

// 2. ToC generator visitor
GenerateToC tocGen = new GenerateToC();
noSqlBook.accept(tocGen);
TableOfContents toc = tocGen.getToC();
toc.print();

// 3. Render content visitor
RenderContentVisitor renderer = new RenderContentVisitor();
noSqlBook.accept(renderer);
```

---

## 6. Future Roadmap

- **Persistence (JPA/Hibernate)**: When persistence is ready, mapping the composite hierarchy using JPA entities with inheritance strategies.
- **REST Controller & Drive Services**: Uploading, organizing, and versioning documents in a Google Drive-like API.
