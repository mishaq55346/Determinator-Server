package ru.mikhail.server.service;

import ru.mikhail.server.model.Book;
import ru.mikhail.server.model.BookDTO;
import ru.mikhail.server.model.Role;

import java.util.List;

public interface BookService {
    List<BookDTO> findBooks(String searchString, Role userRole);

    List<BookDTO> getAll(Role userRole);

    Book getBook(long id);
}
