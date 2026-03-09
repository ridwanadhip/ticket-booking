package id.my.ridwanadhip.ticketbooking.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
