package ru.mikhail.server.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId() && getYear() == book.getYear() && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getRoles(), book.getRoles()) && Objects.equals(getContent(), book.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getTitle(), getYear(), getRoles(), getContent());
    }
}