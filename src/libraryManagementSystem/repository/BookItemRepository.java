package libraryManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import libraryManagementSystem.model.book.BookItem;

public interface BookItemRepository {
    void save(BookItem bookItem);

    Optional<BookItem> findByCopyId(String copyId);

    List<BookItem> findByBookIsbn(String isbn);
}
