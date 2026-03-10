package id.my.ridwanadhip.ticketbooking.booking;

import java.time.LocalDateTime;

public record BookingDTO(
        long id,
        long userId,
        long eventId,
        String ticketSerial,
        LocalDateTime bookedAt,
        String status
) {

}
