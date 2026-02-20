package id.my.ridwanadhip.ticketbooking.booking;

import id.my.ridwanadhip.ticketbooking.event.Event;
import id.my.ridwanadhip.ticketbooking.event.EventRepository;
import id.my.ridwanadhip.ticketbooking.user.User;
import id.my.ridwanadhip.ticketbooking.user.UserRepository;
import id.my.ridwanadhip.ticketbooking.util.Generator;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/booking")
public class BookingController {
    public static final String BOOKING_SERIAL_PREFIX = "BOOK";

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public BookingController(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            EventRepository eventRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping(path="/ticket", consumes = "application/json")
    public List<Booking> bookEvent(
            @RequestBody BookEvent request
    ) {
        var userEmail = request.userEmail();
        var eventId = request.eventId();

        // check if user exists
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        var userId = user.get().getId();

        // generate booking data for each purchased ticket
        return GenerateBookingData(userId, eventId, request.requestedTicket());
    }

    @Transactional
    public List<Booking> GenerateBookingData(long userId, long eventId, int totalTicket) {
        if (!isTicketAvailable(eventId, totalTicket)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No more tickets available");
        }

        // generate booking data for each purchased ticket
        var newBooking = new ArrayList<Booking>();
        for (int i=0; i<totalTicket; i++) {
            var serial = Generator.generateRandomStringWithPrefix(BOOKING_SERIAL_PREFIX);
            newBooking.add(Booking.NewBooking(userId, eventId, serial));
        }

        return bookingRepository.saveAll(newBooking);
    }

    public boolean isTicketAvailable(long eventId, int requestedTicket) {
        // check if event exists
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        // check if user's total ticket is exceeding limit
        if (requestedTicket > event.get().getMaxTicketPerUser()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requested ticket is more than the limit");
        }

        // TODO: check previously booked ticket from the same user

        // check if remaning ticket is more than the user requested ticket.
        // formula: total of user ticket <= event max ticket - all booked ticket
        var totalBookedTicket = bookingRepository.countByEventIdAndStatus(eventId, BookingStatus.BOOKED.toString());
        return requestedTicket <= event.get().getTotalTicket() - totalBookedTicket;
    }
}
