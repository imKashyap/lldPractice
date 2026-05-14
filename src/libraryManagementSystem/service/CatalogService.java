package libraryManagementSystem.service;

import java.util.List;

import libraryManagementSystem.model.book.Book;
import libraryManagementSystem.model.book.BookItem;
import libraryManagementSystem.repository.BookItemRepository;
import libraryManagementSystem.repository.BookRepository;
import libraryManagementSystem.search.SearchStrategy;

public class CatalogService {
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;

    public CatalogService(BookRepository bookRepository, BookItemRepository bookItemRepository) {
        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void addBookItem(BookItem bookItem) {
        bookItemRepository.save(bookItem);
    }

    public void removeBook(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public List<Book> search(SearchStrategy searchStrategy) {
        return searchStrategy.search(bookRepository.findAll());
    }
}
