package org.sp.bookish.entity;

public class Context {
    private int charactersPerPage;

    public Context() {
    }

    public Context(int charactersPerPage) {
        this.charactersPerPage = charactersPerPage;
    }

    public int getCharactersPerPage() {
        return charactersPerPage;
    }

    public void setCharactersPerPage(int charactersPerPage) {
        this.charactersPerPage = charactersPerPage;
    }
}
