package id.my.ridwanadhip.ticketbooking.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
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
        return Booking.builder()
                .userId(userId)
                .eventId(eventId)
                .ticketSerial(ticketSerial)
                .bookedAt(LocalDateTime.now())
                .status(BookingStatus.BOOKED.toString())
                .build();
    }
}
