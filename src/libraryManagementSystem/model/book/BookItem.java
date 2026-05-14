package libraryManagementSystem.model.book;

public class BookItem {
    private final String copyId;
    private final Book book;
    private final String rackLocation;
    private volatile BookItemStatus status;

    public BookItem(String copyId, Book book, String rackLocation, BookItemStatus status) {
        this.copyId = copyId;
        this.book = book;
        this.rackLocation = rackLocation;
        this.status = status;
    }

    public String getCopyId() {
        return copyId;
    }

    public Book getBook() {
        return book;
    }

    public String getRackLocation() {
        return rackLocation;
    }

    public synchronized BookItemStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(BookItemStatus status) {
        this.status = status;
    }
}
