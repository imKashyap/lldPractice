package libraryManagementSystem.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import libraryManagementSystem.model.book.BookItemStatus;
import libraryManagementSystem.repository.BookItemRepository;
import libraryManagementSystem.repository.BookRepository;
import libraryManagementSystem.repository.LoanRepository;

public class InMemoryReportService implements ReportService {
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;
    private final LoanRepository loanRepository;

    public InMemoryReportService(
            BookRepository bookRepository,
            BookItemRepository bookItemRepository,
            LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public String generateAvailabilityReport() {
        StringBuilder report = new StringBuilder("Availability Report\n");
        bookRepository.findAll().forEach(book -> {
            long totalCopies = bookItemRepository.findByBookIsbn(book.getIsbn()).size();
            long availableCopies = bookItemRepository.findByBookIsbn(book.getIsbn()).stream()
                    .filter(bookItem -> bookItem.getStatus() == BookItemStatus.AVAILABLE)
                    .count();
            report.append(book.getTitle())
                    .append(" -> available: ")
                    .append(availableCopies)
                    .append("/")
                    .append(totalCopies)
                    .append("\n");
        });
        return report.toString().trim();
    }

    @Override
    public String generateOverdueReport() {
        String overdueEntries = loanRepository.findOpenLoans().stream()
                .filter(loan -> loan.isOverdue(LocalDate.now()))
                .map(loan -> loan.getLoanId() + " | " + loan.getBookItem().getBook().getTitle()
                        + " | " + loan.getMember().getAccount().getUsername()
                        + " | due " + loan.getDueDate())
                .collect(Collectors.joining("\n"));

        if (overdueEntries.isEmpty()) {
            return "Overdue Report\nNo overdue loans";
        }
        return "Overdue Report\n" + overdueEntries;
    }

    @Override
    public String generatePopularBooksReport() {
        Map<String, Long> titleToOpenLoanCount = loanRepository.findOpenLoans().stream()
                .collect(Collectors.groupingBy(
                        loan -> loan.getBookItem().getBook().getTitle(),
                        TreeMap::new,
                        Collectors.counting()));

        if (titleToOpenLoanCount.isEmpty()) {
            return "Popular Books Report\nNo active loans yet";
        }

        String reportLines = titleToOpenLoanCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> entry.getKey() + " -> active loans: " + entry.getValue())
                .collect(Collectors.joining("\n"));
        return "Popular Books Report\n" + reportLines;
    }
}
