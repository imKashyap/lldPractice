package libraryManagementSystem.search;

import java.util.List;

import libraryManagementSystem.model.book.Book;
import libraryManagementSystem.model.book.Category;

public class SearchByCategoryStrategy implements SearchStrategy {
    private final Category category;

    public SearchByCategoryStrategy(Category category) {
        this.category = category;
    }

    @Override
    public List<Book> search(List<Book> books) {
        return books.stream()
                .filter(book -> book.getCategory() == category)
                .toList();
    }
}
