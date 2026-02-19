package id.my.ridwanadhip.ticketbooking.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    private LocalDateTime bookingStartAt;
    private LocalDateTime bookingFinishAt;
    private int totalTicket;
    private int maxTicketPerUser;
    private double ticketPrice;
    private long venueId;

    public Event() {}

    public Event(
            Long id,
            String name,
            String imageUrl,
            String description,
            LocalDateTime startAt,
            LocalDateTime finishAt,
            LocalDateTime bookingStartAt,
            LocalDateTime bookingFinishAt,
            int totalTicket,
            int maxTicketPerUser,
            double ticketPrice,
            long venueId
    ) {

        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.startAt = startAt;
        this.finishAt = finishAt;
        this.bookingStartAt = bookingStartAt;
        this.bookingFinishAt = bookingFinishAt;
        this.totalTicket = totalTicket;
        this.maxTicketPerUser = maxTicketPerUser;
        this.ticketPrice = ticketPrice;
        this.venueId = venueId;
    }

    public LocalDateTime getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(LocalDateTime finishAt) {
        this.finishAt = finishAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getBookingStartAt() {
        return bookingStartAt;
    }

    public void setBookingStartAt(LocalDateTime bookingStartAt) {
        this.bookingStartAt = bookingStartAt;
    }

    public LocalDateTime getBookingFinishAt() {
        return bookingFinishAt;
    }

    public void setBookingFinishAt(LocalDateTime bookingFinishAt) {
        this.bookingFinishAt = bookingFinishAt;
    }

    public int getTotalTicket() {
        return totalTicket;
    }

    public void setTotalTicket(int totalTicket) {
        this.totalTicket = totalTicket;
    }

    public int getMaxTicketPerUser() {
        return maxTicketPerUser;
    }

    public void setMaxTicketPerUser(int maxTicketPerUser) {
        this.maxTicketPerUser = maxTicketPerUser;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public long getVenueId() {
        return venueId;
    }

    public void setVenueId(long venueId) {
        this.venueId = venueId;
    }
}
