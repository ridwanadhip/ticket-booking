package id.my.ridwanadhip.ticketbooking.venue;

public record VenueDTO(
        long id,
        String name,
        String imageUrl,
        String address,
        String city,
        String country
) {

    public static VenueDTO fromVenue(Venue venue) {
        return new VenueDTO(
                venue.getId(),
                venue.getName(),
                venue.getImageUrl(),
                venue.getAddress(),
                venue.getCity(),
                venue.getCountry()
        );
    }
}
