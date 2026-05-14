package libraryManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import libraryManagementSystem.model.loan.Loan;

public interface LoanRepository {
    void save(Loan loan);

    Optional<Loan> findById(String loanId);

    List<Loan> findByMemberId(String memberId);

    List<Loan> findOpenLoans();
}
