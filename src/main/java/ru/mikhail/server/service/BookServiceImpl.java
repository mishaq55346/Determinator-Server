package ru.mikhail.server.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import ru.mikhail.server.model.Book;
import ru.mikhail.server.model.BookDTO;
import ru.mikhail.server.model.Role;
import ru.mikhail.server.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    protected final static Log logger = LogFactory.getLog(BookServiceImpl.class);


    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public List<BookDTO> findBooks(String searchString, Role userRole) {
        return bookRepository.findAll().stream()
                .filter(book -> visibleForRole(book.getRoles(), userRole))
                .filter(book -> book.getAuthor().contains(searchString) || book.getTitle().contains(searchString))
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAll(Role userRole) {
        return bookRepository.findAll().stream()
                .filter(book -> visibleForRole(book.getRoles(), userRole))
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Book getBook(long id) {
        return bookRepository.getById(id);
    }

    private boolean visibleForRole(String bookRoles, Role userRole) {
        String[] roles = bookRoles.split(",");
        for (String bookRoleValue : roles) {
            if (bookRoleValue.isEmpty()) {
                logger.error("book role value is null");
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
