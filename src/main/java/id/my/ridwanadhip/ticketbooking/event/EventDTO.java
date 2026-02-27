package id.my.ridwanadhip.ticketbooking.event;

import java.time.LocalDateTime;

public record EventDTO(
        long id,
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

    public static EventDTO fromEvent(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getImageUrl(),
                event.getDescription(),
                event.getStartAt(),
                event.getFinishAt(),
                event.getBookingStartAt(),
                event.getBookingFinishAt(),
                event.getTotalTicket(),
                event.getMaxTicketPerUser(),
                event.getTicketPrice(),
                event.getVenueId()
        );
    }
}
