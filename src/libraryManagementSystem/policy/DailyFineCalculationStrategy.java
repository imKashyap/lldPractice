package libraryManagementSystem.policy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import libraryManagementSystem.model.loan.Loan;

public class DailyFineCalculationStrategy implements FineCalculationStrategy {
    private final double dailyRate;

    public DailyFineCalculationStrategy(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    @Override
    public double calculateFine(Loan loan, LocalDate returnedOn) {
        if (!returnedOn.isAfter(loan.getDueDate())) {
            return 0;
        }

        long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), returnedOn);
        return overdueDays * dailyRate;
    }
}
