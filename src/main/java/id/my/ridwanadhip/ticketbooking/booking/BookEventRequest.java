package id.my.ridwanadhip.ticketbooking.booking;

public record BookEventRequest(
        String userEmail,
        long eventId,
        int requestedTicket
) {

}
