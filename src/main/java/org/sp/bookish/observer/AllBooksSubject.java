package org.sp.bookish.observer;

import org.sp.bookish.entity.Book;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AllBooksSubject implements Subject {

    private final List<Observer> observerCollection = new CopyOnWriteArrayList<>();
    private final List<Book> books = new CopyOnWriteArrayList<>();
    private Book latestBook;

    @Override
    public void registerObserver(Observer observer) {
        if (observer != null && !observerCollection.contains(observer)) {
            observerCollection.add(observer);
        }
    }

    @Override
    public void unregisterObserver(Observer observer) {
        if (observer != null) {
            observerCollection.remove(observer);
        }
    }

    @Override
    public void attach(Observer observer) {
        registerObserver(observer);
    }

    @Override
    public void detach(Observer observer) {
        unregisterObserver(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerCollection) {
            observer.update(latestBook);
        }
    }

    public void notifyObservers(Book book) {
        this.latestBook = book;
        for (Observer observer : observerCollection) {
            observer.update(book);
        }
    }

    public void add(Book book) {
        if (book != null) {
            this.books.add(book);
            this.latestBook = book;
            notifyObservers(book);
        }
    }

    public List<Observer> getObserverCollection() {
        return Collections.unmodifiableList(observerCollection);
    }

    public List<Observer> getObservers() {
        return Collections.unmodifiableList(observerCollection);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public Book getLatestBook() {
        return latestBook;
    }
}
