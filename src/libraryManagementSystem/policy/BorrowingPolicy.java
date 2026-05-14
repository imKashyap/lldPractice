package libraryManagementSystem.policy;

import libraryManagementSystem.model.book.BookItem;
import libraryManagementSystem.model.person.Member;

public interface BorrowingPolicy {
    boolean canBorrow(Member member, BookItem item, int activeLoanCount, double unpaidFineAmount);
}
