package org.sp.bookish.controllers;

import org.sp.bookish.difexample.SingletonComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final SingletonComponent singletonComponent;

    @Autowired
    public HelloController(SingletonComponent singletonComponent) {
        this.singletonComponent = singletonComponent;
    }

    @GetMapping("/")
    public String hello() {
        singletonComponent.operation();
        return "Hello from " + singletonComponent;
    }
}
