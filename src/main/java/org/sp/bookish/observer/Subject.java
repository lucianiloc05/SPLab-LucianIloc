package org.sp.bookish.observer;

public interface Subject {
    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void notifyObservers();

    default void attach(Observer observer) {
        registerObserver(observer);
    }

    default void detach(Observer observer) {
        unregisterObserver(observer);
    }
}
