package id.my.ridwanadhip.ticketbooking.venue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService venueService;

    private Venue venue1;
    private Venue venue2;
    private FindAllVenueRequest request;

    @BeforeEach
    void setUp() {
        venue1 = Venue.builder()
                .id(1L)
                .name("Stadium A")
                .imageUrl("https://example.com/image1.jpg")
                .address("123 Main St")
                .city("Jakarta")
                .country("Indonesia")
                .build();

        venue2 = Venue.builder()
                .id(2L)
                .name("Arena B")
                .imageUrl("https://example.com/image2.jpg")
                .address("456 Oak Ave")
                .city("Surabaya")
                .country("Indonesia")
                .build();

        request = new FindAllVenueRequest("Jakarta", "", 0, 10);
    }

    @Test
    void findById_returnsVenueDTO_whenVenueExists() {
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue1));

        Optional<VenueDTO> result = venueService.findById(1L);

        assertTrue(result.isPresent());

        var resultData = result.get();
        assertEquals(1L, resultData.id());
        assertEquals("Stadium A", resultData.name());
        assertEquals("Jakarta", resultData.city());
        assertEquals("Indonesia", resultData.country());
        verify(venueRepository).findById(1L);
    }

    @Test
    void findById_returnsEmptyWhenVenueNotFound() {
        when(venueRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<VenueDTO> result = venueService.findById(99L);

        assertFalse(result.isPresent());
        verify(venueRepository).findById(99L);
    }

    @Test
    void findAll_withCitiesReturnsFilteredVenues() {
        List<Venue> venues = List.of(venue1);
        Pageable pageable = PageRequest.of(0, 10);
        when(venueRepository.findAll(any(Specification.class), any())).
                thenReturn(new PageImpl<>(venues, pageable, venues.size()));

        var req = new FindAllVenueRequest("Jakarta", "", 0, 0);
        List<VenueDTO> result = venueService.findAll(req);

        assertEquals(1, result.size());
        assertEquals("Stadium A", result.getFirst().name());
        verify(venueRepository).findAll(any(Specification.class), any());
    }

    @Test
    void findAll_withCitiesAndCountriesReturnsFilteredVenues() {
        List<Venue> venues = List.of(venue2);
        Pageable pageable = PageRequest.of(0, 10);
        when(venueRepository.findAll(any(Specification.class), any())).
                thenReturn(new PageImpl<>(venues, pageable, venues.size()));

        var req = new FindAllVenueRequest("Surabaya", "Indonesia", 0, 0);
        List<VenueDTO> result = venueService.findAll(req);

        assertEquals(1, result.size());
        assertEquals("Arena B", result.getFirst().name());
        verify(venueRepository).findAll(any(Specification.class), any());
    }

    @Test
    void findAll_withEmptyCityAndCountryReturnsAllVenues() {
        List<Venue> venues = List.of(venue1, venue2);
        Pageable pageable = PageRequest.of(0, 10);
        when(venueRepository.findAll(any(Specification.class), any())).
                thenReturn(new PageImpl<>(venues, pageable, venues.size()));

        request = new FindAllVenueRequest("", "", 0, 10);
        List<VenueDTO> result = venueService.findAll(request);

        assertEquals(2, result.size());
        assertEquals("Stadium A", result.getFirst().name());
        assertEquals("Arena B", result.get(1).name());
    }

    @Test
    void findAll_withPageSizeReturnsCorrectPageable() {
        List<Venue> venues = List.of(venue1, venue2);
        Pageable pageable = PageRequest.of(0, 1);
        when(venueRepository.findAll(any(Specification.class), any())).
                thenReturn(new PageImpl<>(venues, pageable, venues.size()));

        request = new FindAllVenueRequest("", "", 0, 1);
        List<VenueDTO> result = venueService.findAll(request);

        assertEquals(1, result.size());
    }

    @Test
    void findAll_throwsException_whenRepositoryReturnsEmpty() {
        when(venueRepository.findAll(any(Specification.class), any())).
                thenReturn(Page.empty());

        List<VenueDTO> result = venueService.findAll(request);
        assertTrue(result.isEmpty());
    }
}
