package org.sp.bookish.controllers;

import org.sp.bookish.observer.AllBooksSubject;
import org.sp.bookish.observer.SseObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class BooksSseController {

    private final AllBooksSubject allBooksSubject;

    @Autowired
    public BooksSseController(AllBooksSubject allBooksSubject) {
        this.allBooksSubject = allBooksSubject;
    }

    @RequestMapping("/books-sse")
    public ResponseBodyEmitter getBooksSse() {
        final SseEmitter emitter = new SseEmitter(0L);
        final SseObserver observer = new SseObserver(emitter);
        allBooksSubject.attach(observer);
        emitter.onCompletion(() -> allBooksSubject.detach(observer));
        emitter.onTimeout(() -> allBooksSubject.detach(observer));
        emitter.onError(e -> allBooksSubject.detach(observer));
        return emitter;
    }

    public AllBooksSubject getAllBooksSubject() {
        return allBooksSubject;
    }
}
