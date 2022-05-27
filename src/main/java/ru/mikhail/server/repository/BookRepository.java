package ru.mikhail.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikhail.server.model.Book;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
}