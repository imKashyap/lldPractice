package libraryManagementSystem.model.fine;

import libraryManagementSystem.model.person.Member;

public class Fine {
    private final String fineId;
    private final Member member;
    private final double amount;
    private volatile FineStatus status;

    public Fine(String fineId, Member member, double amount, FineStatus status) {
        this.fineId = fineId;
        this.member = member;
        this.amount = amount;
        this.status = status;
    }

    public String getFineId() {
        return fineId;
    }

    public Member getMember() {
        return member;
    }

    public double getAmount() {
        return amount;
    }

    public synchronized FineStatus getStatus() {
        return status;
    }

    public synchronized void markPaid() {
        this.status = FineStatus.PAID;
    }
}
