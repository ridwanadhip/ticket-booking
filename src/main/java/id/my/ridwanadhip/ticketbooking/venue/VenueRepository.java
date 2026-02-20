package id.my.ridwanadhip.ticketbooking.venue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue,Long> {
    List<VenueSummary> findAllByIdIn(List<Long> ids);
    Page<Venue> findAll(Specification<Venue> spec, Pageable pageable);
}
