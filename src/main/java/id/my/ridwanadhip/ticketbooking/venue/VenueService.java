package id.my.ridwanadhip.ticketbooking.venue;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    public  VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Optional<VenueDTO> findById(long id) {
        var venue = venueRepository.findById(id);
        return venue.map(VenueDTO::fromVenue);
    }

    public List<VenueDTO> findAll(FindAllVenueRequest request) {
        Specification<Venue> filter = Specification.unrestricted();

        if (!request.city().isBlank()) {
            filter = filter.and(FilterBuilder.venueHasCity(request.city()));
        }

        if (!request.country().isBlank()) {
            filter = filter.and(FilterBuilder.venueHasCountry(request.country()));
        }

        Pageable paging = PageRequest.of(request.page(), request.pageSize());

        return venueRepository.
                findAll(filter, paging).
                map(VenueDTO::fromVenue).
                getContent();
    }
}
