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

    public static BookingDTO fromBooking(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getUserId(),
                booking.getEventId(),
                booking.getTicketSerial(),
                booking.getBookedAt(),
                booking.getStatus()
        );
    }
}
