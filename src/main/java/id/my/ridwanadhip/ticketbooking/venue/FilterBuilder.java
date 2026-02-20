package id.my.ridwanadhip.ticketbooking.venue;

import org.springframework.data.jpa.domain.Specification;

public class FilterBuilder {
    public static Specification<Venue> venueHasCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<Venue> venueHasCountry(String country) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("country"), country);
    }
}
