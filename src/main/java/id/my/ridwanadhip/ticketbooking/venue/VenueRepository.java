package id.my.ridwanadhip.ticketbooking.venue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue,Long> {
    List<VenueSummary> findAllSummaryByIdIn(List<Long> ids);
    Optional<VenueSummary> findSummaryById(long id);
    Page<Venue> findAll(Specification<Venue> spec, Pageable pageable);
}
