package ru.mikhail.server.model;

import lombok.Data;

@Data
public class BookDTO {
    private long id;
    private String author;
    private String title;
    private int year;
    private String roles;

    public BookDTO(long id, String author, String title, int year, String roles) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.year = year;
        this.roles = roles;
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.author = book.getAuthor();
        this.title = book.getTitle();
        this.year = book.getYear();
        this.roles = book.getRoles();
    }
}
