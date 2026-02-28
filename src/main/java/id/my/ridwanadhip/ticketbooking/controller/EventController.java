package id.my.ridwanadhip.ticketbooking.controller;

import id.my.ridwanadhip.ticketbooking.event.*;
import id.my.ridwanadhip.ticketbooking.venue.VenueRepository;
import id.my.ridwanadhip.ticketbooking.venue.VenueSummary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDTO> findAll(
            @RequestParam(name = "finishAfter", required = false) LocalDateTime finishAfter,
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "description", defaultValue = "") String description,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {

        // TODO: using like with both prefix and suffix is slow. For further performance improvement, we can use search engine such as Elastisearch or Manticore
        if (name.trim().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name search phrase must be 3 characters or more");
        }

        if (description.trim().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description search phrase must be 3 characters or more");
        }

        return eventService.findAll(finishAfter, name, description, page, pageSize);
    }

    @GetMapping(path = "/venue/{venueId}")
    public List<EventDTO> findAllByVenueId(
            @PathVariable long venueId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        return eventService.findAllByVenueId(venueId, page, pageSize);
    }

    @GetMapping(path = "/upcoming")
    public List<EventDetail> findUpcomingEvents(
            @RequestParam(name = "until", required = false) Optional<LocalDateTime> until,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {

        return eventService.findUpcomingEvents(until, page, pageSize);
    }

    @GetMapping(path = "/bookable")
    public List<EventDetail> findBookableEvents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {

        return eventService.findBookableEvents(page, pageSize);
    }

    @GetMapping(path = "/{id}")
    public EventDetail findById(
            @PathVariable long id
    ) {
        Optional<EventDetail> event = eventService.findById(id);
        if (event.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        return event.get();
    }
}
