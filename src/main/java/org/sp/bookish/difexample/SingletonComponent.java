package org.sp.bookish.difexample;

import org.springframework.stereotype.Component;

@Component
public class SingletonComponent {

    public SingletonComponent() {
        System.out.println("SingletonComponent::SingletonComponent = " + this);
    }

    public void operation() {
        System.out.println("Invoked SingletonComponent::operation() on " + this);
    }

    public String getMessage() {
        return "Hello from " + this;
    }
}
