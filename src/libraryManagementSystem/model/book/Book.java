package libraryManagementSystem.model.book;

import java.time.LocalDate;
import java.util.List;

public class Book {
    private final String isbn;
    private final String title;
    private final List<String> authors;
    private final String subject;
    private final LocalDate publicationDate;
    private final Category category;
    private final boolean referenceOnly;

    public Book(
            String isbn,
            String title,
            List<String> authors,
            String subject,
            LocalDate publicationDate,
            Category category,
            boolean referenceOnly) {
        this.isbn = isbn;
        this.title = title;
        this.authors = List.copyOf(authors);
        this.subject = subject;
        this.publicationDate = publicationDate;
        this.category = category;
        this.referenceOnly = referenceOnly;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isReferenceOnly() {
        return referenceOnly;
    }
}
