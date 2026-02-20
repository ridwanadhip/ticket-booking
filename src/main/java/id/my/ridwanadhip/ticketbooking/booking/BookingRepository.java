package id.my.ridwanadhip.ticketbooking.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    long countByEventIdAndStatus(long eventId, String status);
    long countByEventIdAndUserIdAndStatus(long eventId, long userId, String status);
}
