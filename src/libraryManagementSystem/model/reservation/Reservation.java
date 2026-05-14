package libraryManagementSystem.model.reservation;

import java.time.LocalDate;

import libraryManagementSystem.model.book.Book;
import libraryManagementSystem.model.person.Member;

public class Reservation {
    private final String reservationId;
    private final Book book;
    private final Member member;
    private final LocalDate createdAt;
    private volatile ReservationStatus status;

    public Reservation(String reservationId, Book book, Member member, LocalDate createdAt) {
        this.reservationId = reservationId;
        this.book = book;
        this.member = member;
        this.createdAt = createdAt;
        this.status = ReservationStatus.ACTIVE;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public synchronized ReservationStatus getStatus() {
        return status;
    }

    public synchronized void markFulfilled() {
        this.status = ReservationStatus.FULFILLED;
    }
}
