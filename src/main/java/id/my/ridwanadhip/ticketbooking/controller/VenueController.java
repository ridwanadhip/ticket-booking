package id.my.ridwanadhip.ticketbooking.controller;

import id.my.ridwanadhip.ticketbooking.venue.FindAllVenueRequest;
import id.my.ridwanadhip.ticketbooking.venue.VenueDTO;
import id.my.ridwanadhip.ticketbooking.venue.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/venue")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public List<VenueDTO> findAll(
            @RequestParam(name = "city", defaultValue = "") String city,
            @RequestParam(name = "country", defaultValue = "") String country,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        if (!city.isBlank() && city.trim().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City search phrase must be 3 characters or more");
        }

        if (!country.isBlank() && country.trim().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country search phrase must be 3 characters or more");
        }

        var request = new FindAllVenueRequest(city, country, page, pageSize);
        return venueService.findAll(request);
    }

    @GetMapping(path = "/{id}")
    public VenueDTO findById(
            @PathVariable long id
    ) {
        var venue = venueService.findById(id);
        if (venue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found");
        }

        return venue.get();
    }
}
