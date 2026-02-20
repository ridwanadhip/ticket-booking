package id.my.ridwanadhip.ticketbooking.venue;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/venue")
public class VenueController {
    private final VenueRepository venueRepository;

    public VenueController(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @GetMapping
    public List<Venue> findAll(
            @RequestParam(name = "city", defaultValue = "") String city,
            @RequestParam(name = "country", defaultValue = "") String country,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Specification<Venue> filter = Specification.unrestricted();

        if (!city.isBlank()) {
            filter = filter.and(FilterBuilder.venueHasCity(city));
        }

        if (!country.isBlank()) {
            filter = filter.and(FilterBuilder.venueHasCountry(country));
        }

        Pageable paging = PageRequest.of(page, pageSize);
        return venueRepository.findAll(filter, paging).getContent();
    }

    @GetMapping(path = "/{id}")
    public Venue findById(
            @PathVariable long id
    ) {
        var findResult = venueRepository.findById(id);
        if (findResult.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found");
        }

        return findResult.get();
    }
}
