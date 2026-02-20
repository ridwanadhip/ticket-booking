package id.my.ridwanadhip.ticketbooking.event;

import id.my.ridwanadhip.ticketbooking.venue.VenueSummary;

import java.time.LocalDateTime;

public record EventDetail(
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
        long venueId,
        String venueName,
        String venueCity,
        String venueCountry
) {

    public static EventDetail from(Event event, VenueSummary venue) {
        return new EventDetail(
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
                event.getVenueId(),
                venue.getName(),
                venue.getCity(),
                venue.getCountry()
        );
    }
}
