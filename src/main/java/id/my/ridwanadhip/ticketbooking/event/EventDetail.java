package id.my.ridwanadhip.ticketbooking.event;

import java.time.LocalDateTime;

record EventVenue (
        long id,
        String name,
        String city,
        String country
) {

}

public record EventDetail (
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
        EventVenue venue
) {

}
