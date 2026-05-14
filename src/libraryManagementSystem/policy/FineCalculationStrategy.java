package libraryManagementSystem.policy;

import java.time.LocalDate;

import libraryManagementSystem.model.loan.Loan;

public interface FineCalculationStrategy {
    double calculateFine(Loan loan, LocalDate returnedOn);
}
