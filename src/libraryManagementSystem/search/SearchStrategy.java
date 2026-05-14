package libraryManagementSystem.search;

import java.util.List;

import libraryManagementSystem.model.book.Book;

public interface SearchStrategy {
    List<Book> search(List<Book> books);
}
