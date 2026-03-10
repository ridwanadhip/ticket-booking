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

}
