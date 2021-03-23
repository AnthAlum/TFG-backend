package backend.meetings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    @Query("select m from Meeting m")
    public Page<Meeting> searchMeetings(Pageable pageable);

    Page<Meeting> findByMatterContains(String matter, Pageable pageable);
    long countByMatterContains(String matter);
}
