package libraryManagementSystem.model.loan;

import java.time.LocalDate;

import libraryManagementSystem.model.book.BookItem;
import libraryManagementSystem.model.person.Member;

public class Loan {
    private final String loanId;
    private final Member member;
    private final BookItem bookItem;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private volatile LocalDate returnDate;
    private volatile LoanStatus status;

    public Loan(String loanId, Member member, BookItem bookItem, LocalDate checkoutDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.member = member;
        this.bookItem = bookItem;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.status = LoanStatus.OPEN;
    }

    public String getLoanId() {
        return loanId;
    }

    public Member getMember() {
        return member;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public synchronized LocalDate getReturnDate() {
        return returnDate;
    }

    public synchronized LoanStatus getStatus() {
        return status;
    }

    public synchronized void markReturned(LocalDate returnedOn) {
        this.returnDate = returnedOn;
        this.status = LoanStatus.RETURNED;
    }

    public synchronized boolean isOverdue(LocalDate today) {
        return status == LoanStatus.OPEN && today.isAfter(dueDate);
    }
}
