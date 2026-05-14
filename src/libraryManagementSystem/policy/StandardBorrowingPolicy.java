package libraryManagementSystem.policy;

import libraryManagementSystem.model.book.BookItem;
import libraryManagementSystem.model.book.BookItemStatus;
import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.model.person.MemberStatus;

public class StandardBorrowingPolicy implements BorrowingPolicy {
    private final int maxAllowedLoans;
    private final double fineThreshold;

    public StandardBorrowingPolicy(int maxAllowedLoans, double fineThreshold) {
        this.maxAllowedLoans = maxAllowedLoans;
        this.fineThreshold = fineThreshold;
    }

    @Override
    public boolean canBorrow(Member member, BookItem item, int activeLoanCount, double unpaidFineAmount) {
        return member.getStatus() == MemberStatus.ACTIVE
                && item.getStatus() == BookItemStatus.AVAILABLE
                && !item.getBook().isReferenceOnly()
                && activeLoanCount < maxAllowedLoans
                && unpaidFineAmount <= fineThreshold;
    }
}
