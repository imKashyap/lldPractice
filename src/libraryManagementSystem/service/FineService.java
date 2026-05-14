package libraryManagementSystem.service;

import java.time.LocalDate;

import libraryManagementSystem.model.fine.Fine;
import libraryManagementSystem.model.fine.FineStatus;
import libraryManagementSystem.model.loan.Loan;
import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.policy.FineCalculationStrategy;
import libraryManagementSystem.repository.FineRepository;

public class FineService {
    private final FineRepository fineRepository;
    private final FineCalculationStrategy fineCalculationStrategy;

    public FineService(FineRepository fineRepository, FineCalculationStrategy fineCalculationStrategy) {
        this.fineRepository = fineRepository;
        this.fineCalculationStrategy = fineCalculationStrategy;
    }

    public double calculateFine(Loan loan, LocalDate returnedOn) {
        return fineCalculationStrategy.calculateFine(loan, returnedOn);
    }

    public double getOutstandingFine(Member member) {
        return fineRepository.findByMemberId(member.getAccount().getUsername()).stream()
                .filter(fine -> fine.getStatus() == FineStatus.UNPAID)
                .mapToDouble(Fine::getAmount)
                .sum();
    }
}
