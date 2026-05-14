package libraryManagementSystem.search;

import java.util.List;

import libraryManagementSystem.model.book.Book;

public class SearchByTitleStrategy implements SearchStrategy {
    private final String title;

    public SearchByTitleStrategy(String title) {
        this.title = title;
    }

    @Override
    public List<Book> search(List<Book> books) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .toList();
    }
}
