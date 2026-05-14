package libraryManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import libraryManagementSystem.model.book.Book;

public interface BookRepository {
    void save(Book book);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findAll();

    void deleteByIsbn(String isbn);
}
