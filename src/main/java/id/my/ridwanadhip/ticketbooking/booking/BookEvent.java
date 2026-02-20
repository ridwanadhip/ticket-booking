package id.my.ridwanadhip.ticketbooking.booking;

public record BookEvent(
        String userEmail,
        long eventId,
        int requestedTicket
) {

}
