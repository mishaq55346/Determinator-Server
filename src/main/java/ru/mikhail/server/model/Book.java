package ru.mikhail.server.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String author;
    private String title;
    private int year;
    @Column(name = "visible_for_roles")
    private String roles;
    private String content;
}