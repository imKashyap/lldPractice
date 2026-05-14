package hotelManagementSystem.models.room;

import java.time.LocalDateTime;

public class KeyCard {
    private final String cardId;
    private Room room;
    private volatile LocalDateTime validFrom;
    private volatile LocalDateTime validTo;
    private volatile CardStatus cardStatus;

    public KeyCard(String cardId, LocalDateTime validFrom, LocalDateTime validTo) {
        this.cardId = cardId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.cardStatus = CardStatus.INACTIVE;
    }

    public synchronized boolean addRoomAccess(Room room) {
        if (cardStatus != CardStatus.ACTIVE) {
            cardStatus = CardStatus.ACTIVE;
        }
        this.room = room;
        return true;
    }

    public synchronized boolean removeRoomAccess() {
        if (cardStatus == CardStatus.ACTIVE) {
            cardStatus = CardStatus.INACTIVE;
            this.room = null;
            return true;
        }
        return false;
    }

    public synchronized void markKeyCardLost() {
        cardStatus = CardStatus.LOST;
    }

    public String getCardId() {
        return cardId;
    }

    public Room getRooms() {
        return room;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public synchronized CardStatus getCardStatus() {
        return cardStatus;
    }

}
