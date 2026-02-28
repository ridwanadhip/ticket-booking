package id.my.ridwanadhip.ticketbooking.booking;

import id.my.ridwanadhip.ticketbooking.event.Event;
import id.my.ridwanadhip.ticketbooking.event.EventRepository;
import id.my.ridwanadhip.ticketbooking.user.User;
import id.my.ridwanadhip.ticketbooking.user.UserRepository;
import id.my.ridwanadhip.ticketbooking.util.StringGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    public static final String BOOKING_SERIAL_PREFIX = "BOOK";

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            EventRepository eventRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    // wrap entire process behind transaction to prevent double booking issue
    @Transactional
    public List<BookingDTO> bookEvent(
            BookEvent request
    ) {
        // check if user exists
        Optional<User> user = userRepository.findByEmail(request.userEmail());
        if (user.isEmpty()) {
            return List.of();
        }

        // generate booking data for each purchased ticket
        var userId = user.get().getId();
        var bookings = generateBookingData(userId, request.eventId(), request.requestedTicket());

        return bookings.stream().map(BookingDTO::fromBooking).toList();
    }

    private List<Booking> generateBookingData(long userId, long eventId, int totalTicket) {
        if (!isTicketAvailable(eventId, userId, totalTicket)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No more tickets available");
        }

        // generate booking data for each purchased ticket
        var newBooking = new ArrayList<Booking>();
        for (int i = 0; i < totalTicket; i++) {
            var suffix = String.valueOf(i + 1); // indicates the ticket number
            var serial = StringGenerator.randomSerial(BOOKING_SERIAL_PREFIX, suffix);
            newBooking.add(Booking.NewBooking(userId, eventId, serial));
        }

        return bookingRepository.saveAll(newBooking);
    }

    private boolean isTicketAvailable(long eventId, long userId, int requestedTicket) {
        // check if event exists
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        var bookedStatus = BookingStatus.BOOKED.toString();
        var previousUserTicket = bookingRepository.countByEventIdAndUserIdAndStatus(eventId, userId, bookedStatus);

        // check if the total of user's previously booked and requested ticket is exceeding limit
        if (requestedTicket + previousUserTicket > event.get().getMaxTicketPerUser()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requested ticket is more than event limit");
        }

        // check if remaining ticket is more than the user requested ticket.
        // formula: total of user ticket <= event max ticket - all booked ticket
        var totalBookedTicket = bookingRepository.countByEventIdAndStatus(eventId, bookedStatus);
        return requestedTicket <= event.get().getTotalTicket() - totalBookedTicket;
    }
}
