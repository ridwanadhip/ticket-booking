package id.my.ridwanadhip.ticketbooking.event;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class FilterBuilder {
    public static Specification<Event> eventNameContains(String phrase) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%%%s%%".formatted(phrase));
    }

    public static Specification<Event> eventDescriptionContains(String phrase) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%%%s%%".formatted(phrase));
    }

    public static Specification<Event> eventFinishAfter(LocalDateTime timestamp) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("finishAt"), timestamp);
    }
}
