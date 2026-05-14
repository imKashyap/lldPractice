package libraryManagementSystem.search;

import java.util.List;

import libraryManagementSystem.model.book.Book;

public class SearchByAuthorStrategy implements SearchStrategy {
    private final String author;

    public SearchByAuthorStrategy(String author) {
        this.author = author;
    }

    @Override
    public List<Book> search(List<Book> books) {
        return books.stream()
                .filter(book -> book.getAuthors().stream().anyMatch(name -> name.equalsIgnoreCase(author)))
                .toList();
    }
}
