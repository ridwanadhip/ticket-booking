package id.my.ridwanadhip.ticketbooking.booking;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/ticket", consumes = "application/json")
    public List<BookingDTO> bookEvent(
            @RequestBody BookEvent request
    ) {
        return bookingService.bookEvent(request);
    }
}
