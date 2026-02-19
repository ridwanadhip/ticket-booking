package id.my.ridwanadhip.ticketbooking.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/event")
public class EventController {
    private static final int DEFAULT_UPCOMING_EVENT_DAY_LIMIT = 7;

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping(path="/venue/{venueId}")
    public List<Event> findAllByVenueId(
            @PathVariable long venueId,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="pageSize", defaultValue = "10") int pageSize
    ) {

        Pageable paging = PageRequest.of(page, pageSize);
        return eventRepository.findAllByVenueId(venueId, paging).getContent();
    }

    @GetMapping(path="/upcoming")
    public List<Event> findUpcomingEvents(
            @RequestParam(name="until", required = false) LocalDateTime until,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="pageSize", defaultValue = "10") int pageSize
    ) {

        var since = LocalDateTime.now();
        if (until == null) {
            until = since.plusDays(DEFAULT_UPCOMING_EVENT_DAY_LIMIT);
        }

        Pageable paging = PageRequest.of(page, pageSize, Sort.by("startAt"));
        return eventRepository.findAllByStartAtBetween(since, until, paging).getContent();
    }

    @GetMapping(path="/bookable")
    public List<Event> findBookableEvents(
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="pageSize", defaultValue = "10") int pageSize
    ) {

        var now = LocalDateTime.now();
        Pageable paging = PageRequest.of(page, pageSize);
        return eventRepository.findAllByBetweenBookingTime(now, paging).getContent();
    }
}
