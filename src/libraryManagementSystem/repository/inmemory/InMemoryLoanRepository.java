package libraryManagementSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import libraryManagementSystem.model.loan.Loan;
import libraryManagementSystem.model.loan.LoanStatus;
import libraryManagementSystem.repository.LoanRepository;

public class InMemoryLoanRepository implements LoanRepository {
    private final Map<String, Loan> loansById = new ConcurrentHashMap<>();

    @Override
    public void save(Loan loan) {
        loansById.put(loan.getLoanId(), loan);
    }

    @Override
    public Optional<Loan> findById(String loanId) {
        return Optional.ofNullable(loansById.get(loanId));
    }

    @Override
    public List<Loan> findByMemberId(String memberId) {
        return loansById.values().stream()
                .filter(loan -> loan.getMember().getAccount().getUsername().equals(memberId))
                .toList();
    }

    @Override
    public List<Loan> findOpenLoans() {
        return loansById.values().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.OPEN)
                .toList();
    }
}
