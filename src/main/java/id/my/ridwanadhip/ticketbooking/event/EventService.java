package id.my.ridwanadhip.ticketbooking.event;

import id.my.ridwanadhip.ticketbooking.venue.VenueRepository;
import id.my.ridwanadhip.ticketbooking.venue.VenueSummary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    private static final int DEFAULT_UPCOMING_EVENT_DAY_LIMIT = 7;

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<EventDTO> findAll(
            LocalDateTime finishAfter,
            String name,
            String description,
            int page,
            int pageSize
    ) {
        Specification<Event> filter = Specification.unrestricted();

        if (finishAfter != null) {
            filter = filter.and(FilterBuilder.eventFinishAfter(finishAfter));
        }

        // TODO: using like with both prefix and suffix is slow. For further performance improvement, we can use search engine such as Elastisearch or Manticore
        if (!name.isBlank()) {
            filter = filter.and(FilterBuilder.eventNameContains(name));
        }

        if (!description.isBlank()) {
            filter = filter.and(FilterBuilder.eventDescriptionContains(description));
        }

        Pageable paging = PageRequest.of(page, pageSize);
        var events = eventRepository.findAll(filter, paging).getContent();
        return events.stream().map(EventDTO::fromEvent).toList();
    }

    public List<EventDTO> findAllByVenueId(long venueId, int page, int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        var events = eventRepository.findAllByVenueId(venueId, paging).getContent();
        return events.stream().map(EventDTO::fromEvent).toList();
    }

    public List<EventDetail> findUpcomingEvents(Optional<LocalDateTime> until, int page, int pageSize) {
        // if time range is not provided then limit data within default range
        var sinceParam = LocalDateTime.now();
        var untilParam = sinceParam.plusDays(DEFAULT_UPCOMING_EVENT_DAY_LIMIT);
        if (until.isPresent()) {
            untilParam = until.get();
        }

        Pageable paging = PageRequest.of(page, pageSize, Sort.by("startAt"));
        List<Event> events = eventRepository.findAllByStartAtBetween(sinceParam, untilParam, paging).getContent();

        var venueSummaryMap = findVenueSummaryFromEvents(events);

        // combine event and venue data
        return events.stream().
                map(event -> {
                    var venueSummary = venueSummaryMap.get(event.getVenueId());
                    return EventDetail.from(event, venueSummary);
                }).toList();
    }

    public List<EventDetail> findBookableEvents(int page, int pageSize) {
        var now = LocalDateTime.now();
        Pageable paging = PageRequest.of(page, pageSize);
        List<Event> events = eventRepository.findAllByBetweenBookingTime(now, paging).getContent();

        var venueSummaryMap = findVenueSummaryFromEvents(events);

        // combine event and venue data
        return events.stream().
                map(event -> {
                    var venueSummary = venueSummaryMap.get(event.getVenueId());
                    return EventDetail.from(event, venueSummary);
                }).toList();
    }

    public Optional<EventDetail> findById(long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            return Optional.empty();
        }

        Optional<VenueSummary> venueSummary = venueRepository.findSummaryById(event.get().getVenueId());
        if (venueSummary.isEmpty()) {
            return Optional.of(EventDetail.from(event.get()));
        }

        return Optional.of(EventDetail.from(event.get(), venueSummary.get()));
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
