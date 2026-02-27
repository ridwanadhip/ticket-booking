package id.my.ridwanadhip.ticketbooking.venue;

public record FindAllVenueRequest(
        String city,
        String country,
        int page,
        int pageSize
) {

}
