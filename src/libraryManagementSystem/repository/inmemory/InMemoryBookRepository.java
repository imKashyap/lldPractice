package libraryManagementSystem.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import libraryManagementSystem.model.book.Book;
import libraryManagementSystem.repository.BookRepository;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> booksByIsbn = new ConcurrentHashMap<>();

    @Override
    public void save(Book book) {
        booksByIsbn.put(book.getIsbn(), book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(booksByIsbn.values());
    }

    @Override
    public void deleteByIsbn(String isbn) {
        booksByIsbn.remove(isbn);
    }
}
