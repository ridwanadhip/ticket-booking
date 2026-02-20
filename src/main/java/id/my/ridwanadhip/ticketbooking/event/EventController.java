package id.my.ridwanadhip.ticketbooking.event;

import id.my.ridwanadhip.ticketbooking.venue.Venue;
import id.my.ridwanadhip.ticketbooking.venue.VenueRepository;
import id.my.ridwanadhip.ticketbooking.venue.VenueSummary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/event")
public class EventController {
    private static final int DEFAULT_UPCOMING_EVENT_DAY_LIMIT = 7;

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventController(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @GetMapping(path = "/venue/{venueId}")
    public List<Event> findAllByVenueId(
            @PathVariable long venueId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {

        Pageable paging = PageRequest.of(page, pageSize);
        return eventRepository.findAllByVenueId(venueId, paging).getContent();
    }

    @GetMapping(path = "/upcoming")
    public List<EventDetail> findUpcomingEvents(
            @RequestParam(name = "until", required = false) LocalDateTime until,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {

        // if time range is not provided then limit data within default range
        var since = LocalDateTime.now();
        if (until == null) {
            until = since.plusDays(DEFAULT_UPCOMING_EVENT_DAY_LIMIT);
        }

        Pageable paging = PageRequest.of(page, pageSize, Sort.by("startAt"));
        List<Event> events = eventRepository.findAllByStartAtBetween(since, until, paging).getContent();

        var venueSummaryMap = findVenueSummaryFromEvents(events);

        // combine event and venue data
        var result = events.stream().
                map(event -> {
                    var venueSummary = venueSummaryMap.get(event.getVenueId());
                    return EventDetail.from(event, venueSummary);
                }).toList();

        return result;
    }

    @GetMapping(path = "/bookable")
    public List<EventDetail> findBookableEvents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {

        var now = LocalDateTime.now();
        Pageable paging = PageRequest.of(page, pageSize);
        List<Event> events = eventRepository.findAllByBetweenBookingTime(now, paging).getContent();

        var venueSummaryMap = findVenueSummaryFromEvents(events);

        // combine event and venue data
        var result = events.stream().
                map(event -> {
                    var venueSummary = venueSummaryMap.get(event.getVenueId());
                    return EventDetail.from(event, venueSummary);
                }).toList();

        return result;
    }

    @GetMapping(path = "/{id}")
    public EventDetail findById(
            @PathVariable long id
    ) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        Optional<VenueSummary> venueSummary = venueRepository.findSummaryById(event.get().getVenueId());
        if (venueSummary.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found");
        }

        return EventDetail.from(event.get(), venueSummary.get());
    }

    private Map<Long, VenueSummary> findVenueSummaryFromEvents(List<Event> events) {
        List<Long> venueIds = events.stream().
                map(Event::getVenueId).
                distinct().
                toList();

        return venueRepository.findAllSummaryByIdIn(venueIds).
                stream().
                collect(Collectors.toMap(VenueSummary::getId, summary -> summary));
    }
}
