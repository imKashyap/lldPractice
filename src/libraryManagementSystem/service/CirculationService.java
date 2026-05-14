package libraryManagementSystem.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import libraryManagementSystem.model.book.BookItem;
import libraryManagementSystem.model.book.BookItemStatus;
import libraryManagementSystem.model.fine.Fine;
import libraryManagementSystem.model.fine.FineStatus;
import libraryManagementSystem.model.loan.Loan;
import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.model.reservation.Reservation;
import libraryManagementSystem.policy.BorrowingPolicy;
import libraryManagementSystem.repository.BookItemRepository;
import libraryManagementSystem.repository.FineRepository;
import libraryManagementSystem.repository.LoanRepository;
import libraryManagementSystem.repository.ReservationRepository;

public class CirculationService {
    private final BookItemRepository bookItemRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final FineRepository fineRepository;
    private final BorrowingPolicy borrowingPolicy;
    private final FineService fineService;
    private final ReadWriteLock circulationLock = new ReentrantReadWriteLock();
    private final Lock writeLock = circulationLock.writeLock();

    public CirculationService(
            BookItemRepository bookItemRepository,
            LoanRepository loanRepository,
            ReservationRepository reservationRepository,
            FineRepository fineRepository,
            BorrowingPolicy borrowingPolicy,
            FineService fineService) {
        this.bookItemRepository = bookItemRepository;
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
        this.fineRepository = fineRepository;
        this.borrowingPolicy = borrowingPolicy;
        this.fineService = fineService;
    }

    public Loan checkout(Member member, String isbn, LocalDate checkoutDate, int loanDurationDays) {
        writeLock.lock();
        try {
            BookItem bookItem = bookItemRepository.findByBookIsbn(isbn).stream()
                    .filter(item -> item.getStatus() == BookItemStatus.AVAILABLE)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No available copy found"));

            int activeLoanCount = (int) loanRepository.findByMemberId(member.getAccount().getUsername()).stream()
                    .filter(loan -> loan.getStatus() == libraryManagementSystem.model.loan.LoanStatus.OPEN)
                    .count();
            double outstandingFine = fineService.getOutstandingFine(member);

            if (!borrowingPolicy.canBorrow(member, bookItem, activeLoanCount, outstandingFine)) {
                throw new IllegalStateException("Borrowing policy validation failed");
            }

            Loan loan = new Loan(
                    "LOAN-" + UUID.randomUUID(),
                    member,
                    bookItem,
                    checkoutDate,
                    checkoutDate.plusDays(loanDurationDays));
            bookItem.setStatus(BookItemStatus.LOANED);
            loanRepository.save(loan);
            bookItemRepository.save(bookItem);
            return loan;
        } finally {
            writeLock.unlock();
        }
    }

    public void returnBook(String loanId, LocalDate returnedOn) {
        writeLock.lock();
        try {
            Loan loan = loanRepository.findById(loanId)
                    .orElseThrow(() -> new IllegalStateException("Loan not found"));

            if (loan.getStatus() != libraryManagementSystem.model.loan.LoanStatus.OPEN) {
                throw new IllegalStateException("Loan is already closed");
            }

            loan.markReturned(returnedOn);
            BookItem bookItem = loan.getBookItem();
            bookItem.setStatus(BookItemStatus.AVAILABLE);
            loanRepository.save(loan);
            bookItemRepository.save(bookItem);

            double fineAmount = fineService.calculateFine(loan, returnedOn);
            if (fineAmount > 0) {
                Fine fine = new Fine("FINE-" + UUID.randomUUID(), loan.getMember(), fineAmount, FineStatus.UNPAID);
                fineRepository.save(fine);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public Reservation reserve(Member member, String isbn, LocalDate reservedOn) {
        writeLock.lock();
        try {
            BookItem firstCopy = bookItemRepository.findByBookIsbn(isbn).stream()
                    .min(Comparator.comparing(BookItem::getCopyId))
                    .orElseThrow(() -> new IllegalStateException("Book not found"));

            Reservation reservation = new Reservation(
                    "RES-" + UUID.randomUUID(),
                    firstCopy.getBook(),
                    member,
                    reservedOn);
            reservationRepository.save(reservation);
            return reservation;
        } finally {
            writeLock.unlock();
        }
    }
}
