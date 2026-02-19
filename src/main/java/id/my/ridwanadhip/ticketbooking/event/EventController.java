package id.my.ridwanadhip.ticketbooking.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/event")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping(path="/{venueId}")
    public List<Event> findAllByVenueId(
            @PathVariable long venueId,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="pageSize", defaultValue = "10") int pageSize
    ) {

        Pageable paging = PageRequest.of(page, pageSize);
        return eventRepository.findAllByVenueId(venueId, paging).getContent();
    }
}
