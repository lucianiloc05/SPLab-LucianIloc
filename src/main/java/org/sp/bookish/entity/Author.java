package org.sp.bookish.entity;

public class Author {
    private String name;
    private String surname;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void print() {
        String fullName = (surname != null && !surname.isBlank()) ? (name + " " + surname) : name;
        System.out.println("Author: " + fullName);
    }
}
