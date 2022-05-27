package ru.mikhail.server.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhail.server.model.Book;
import ru.mikhail.server.model.BookDTO;
import ru.mikhail.server.model.Role;
import ru.mikhail.server.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public List<BookDTO> findBooks(String searchString, Role userRole) {
        return bookRepository.findAll().stream()
                .filter(book -> visibleForRole(book.getRoles(), userRole))
                .filter(book -> book.getAuthor().toUpperCase()
                        .contains(searchString.toUpperCase()) ||
                        book.getTitle().toUpperCase()
                                .contains(searchString.toUpperCase()))
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<BookDTO> getAll(Role userRole) {
        return bookRepository.findAll().stream()
                .filter(book -> visibleForRole(book.getRoles(), userRole))
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Book getBook(long id) {
        return bookRepository.getById(id);
    }

    private boolean visibleForRole(String bookRoles, Role userRole) {
        if (userRole.getUniversityCode().equals("ADMIN")
                || userRole.getUniversityCode().equals("TEST")) {
            return true;
        }
        String[] roles = bookRoles.split(",");
        for (String bookRoleValue : roles) {
            if (bookRoleValue.isEmpty()) {
                log.error("book role value is null");
                continue;
            }

            Role bookRole = new Role(bookRoleValue);
            if (bookRole.getUniversityCode() == null) { // Книга доступна для всех
                return true;
            }
            if (bookRole.getUniversityCode().equals(userRole.getUniversityCode())) {
                return true;
            }
        }
        return false;
    }
}
