package libraryManagementSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import libraryManagementSystem.model.book.Book;
import libraryManagementSystem.model.book.BookItem;
import libraryManagementSystem.model.book.BookItemStatus;
import libraryManagementSystem.model.book.Category;
import libraryManagementSystem.model.loan.Loan;
import libraryManagementSystem.model.person.Account;
import libraryManagementSystem.model.person.ContactInfo;
import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.policy.DailyFineCalculationStrategy;
import libraryManagementSystem.policy.StandardBorrowingPolicy;
import libraryManagementSystem.repository.*;
import libraryManagementSystem.repository.inmemory.*;
import libraryManagementSystem.service.CatalogService;
import libraryManagementSystem.service.CirculationService;
import libraryManagementSystem.service.FineService;
import libraryManagementSystem.service.MembershipService;

public class LibraryManagementSystemDemo {
    public static void main(String[] args) throws Exception {
        BookRepository bookRepository = new InMemoryBookRepository();
        BookItemRepository bookItemRepository = new InMemoryBookItemRepository();
        MemberRepository memberRepository = new InMemoryMemberRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();
        FineRepository fineRepository = new InMemoryFineRepository();
        ReservationRepository reservationRepository = new InMemoryReservationRepository();

        CatalogService catalogService = new CatalogService(bookRepository, bookItemRepository);
        MembershipService membershipService = new MembershipService(memberRepository);
        FineService fineService = new FineService(fineRepository, new DailyFineCalculationStrategy(10));
        CirculationService circulationService = new CirculationService(
                bookItemRepository,
                loanRepository,
                reservationRepository,
                fineRepository,
                new StandardBorrowingPolicy(3, 100),
                fineService);

        Book book = new Book(
                "9780134494166",
                "Effective Java",
                List.of("Joshua Bloch"),
                "Java best practices",
                LocalDate.of(2018, 1, 6),
                Category.TECHNOLOGY,
                false);
        catalogService.addBook(book);
        catalogService.addBookItem(new BookItem("EJ-1", book, "T1-S9", BookItemStatus.AVAILABLE));

        List<Member> members = List.of(
                createMember("user1"),
                createMember("user2"),
                createMember("user3"),
                createMember("user4"),
                createMember("user5"));
        members.forEach(membershipService::registerMember);

        ExecutorService executorService = Executors.newFixedThreadPool(members.size());
        CountDownLatch startLatch = new CountDownLatch(1);
        List<Callable<String>> tasks = new ArrayList<>();

        for (Member member : members) {
            tasks.add(() -> {
                startLatch.await();
                try {
                    Loan loan = circulationService.checkout(member, book.getIsbn(), LocalDate.now(), 7);
                    return member.getAccount().getUsername() + " succeeded with " + loan.getLoanId();
                } catch (IllegalStateException exception) {
                    return member.getAccount().getUsername() + " failed: " + exception.getMessage();
                }
            });
        }

        List<Future<String>> futures = tasks.stream()
                .map(executorService::submit)
                .toList();

        startLatch.countDown();

        int successCount = 0;
        for (Future<String> future : futures) {
            String result = future.get();
            System.out.println(result);
            if (result.contains("succeeded")) {
                successCount++;
            }
        }

        executorService.shutdown();

        System.out.println("Successful checkout count: " + successCount);
        System.out.println("Open loans count: " + loanRepository.findOpenLoans().size());
        System.out.println("Remaining available copies: " + bookItemRepository.findByBookIsbn(book.getIsbn()).stream()
                .filter(bookItem -> bookItem.getStatus() == BookItemStatus.AVAILABLE)
                .count());
    }

    private static Member createMember(String username) {
        return new Member(
                username,
                new ContactInfo(username + "@library.com", "9999999999", "Concurrent Lane"),
                new Account(username, "secret"));
    }
}
