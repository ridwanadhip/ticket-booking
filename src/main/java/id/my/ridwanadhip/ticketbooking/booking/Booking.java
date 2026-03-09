package id.my.ridwanadhip.ticketbooking.booking;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;
    private long eventId;
    private String ticketSerial;
    private LocalDateTime bookedAt;
    private String status;

    public static Booking NewBooking(
            long userId,
            long eventId,
            String ticketSerial
    ) {
        var result = new Booking();
        result.setUserId(userId);
        result.setEventId(eventId);
        result.setTicketSerial(ticketSerial);
        result.setBookedAt(LocalDateTime.now());
        result.setStatus(BookingStatus.BOOKED.toString());

        return result;
    }
}
