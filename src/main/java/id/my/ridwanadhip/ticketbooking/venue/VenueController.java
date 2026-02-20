package id.my.ridwanadhip.ticketbooking.venue;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
