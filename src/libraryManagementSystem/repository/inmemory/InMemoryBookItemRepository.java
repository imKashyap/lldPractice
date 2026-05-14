package libraryManagementSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import libraryManagementSystem.model.book.BookItem;
import libraryManagementSystem.repository.BookItemRepository;

public class InMemoryBookItemRepository implements BookItemRepository {
    private final Map<String, BookItem> bookItemsByCopyId = new ConcurrentHashMap<>();

    @Override
    public void save(BookItem bookItem) {
        bookItemsByCopyId.put(bookItem.getCopyId(), bookItem);
    }

    @Override
    public Optional<BookItem> findByCopyId(String copyId) {
        return Optional.ofNullable(bookItemsByCopyId.get(copyId));
    }

    @Override
    public List<BookItem> findByBookIsbn(String isbn) {
        return bookItemsByCopyId.values().stream()
                .filter(bookItem -> bookItem.getBook().getIsbn().equals(isbn))
                .toList();
    }
}
