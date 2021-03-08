package backend.meetings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    @Query("select m from Meeting m")
    public Page<Meeting> searchMeetings(Pageable pageable);
}
