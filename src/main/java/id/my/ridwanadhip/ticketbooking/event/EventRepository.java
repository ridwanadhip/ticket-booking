package id.my.ridwanadhip.ticketbooking.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByVenueId(Long venueId, Pageable pageable);
    Page<Event> findAllByStartAtBetween(LocalDateTime since, LocalDateTime until, Pageable pageable);

    @NativeQuery("SELECT * FROM event e WHERE ?1 BETWEEN e.booking_start_at and e.booking_finish_at")
    Page<Event> findAllByBetweenBookingTime(LocalDateTime now, Pageable pageable);
}
