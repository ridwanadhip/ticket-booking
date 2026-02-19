package id.my.ridwanadhip.ticketbooking.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;
    private long eventId;
    private String ticketSerial;
    private LocalDate bookedAt;
    private String status;

    public Booking(){}

    public Booking(
            Long id,
            long userId,
            long eventId,
            String ticketSerial,
            LocalDate bookedAt,
            String status
    ) {

        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.ticketSerial = ticketSerial;
        this.bookedAt = bookedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getTicketSerial() {
        return ticketSerial;
    }

    public void setTicketSerial(String ticketSerial) {
        this.ticketSerial = ticketSerial;
    }

    public LocalDate getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDate bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
