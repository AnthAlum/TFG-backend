package backend.meetings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    @Query("select m from Meeting m")
    public Page<Meeting> searchMeetings(Pageable pageable);

    @Query(
            value = "SELECT meet.id_meeting, mer.id_merchant, cli.id_client FROM related_merchant mer, meeting meet, related_client cli " +
                    "WHERE mer.id_meeting = meet.id_meeting AND cli.id_meeting = meet.id_meeting AND " +
                    "(SELECT count(*) FROM public.related_merchant mer2, public.meeting meet2, public.related_client cli2 " +
                    "   WHERE mer2.id_meeting = meet2.id_meeting AND cli2.id_meeting = meet2.id_meeting " +
                    "   AND mer2.id_merchant = mer.id_merchant AND cli2.id_client = cli.id_client " +
                    "   AND meet2.date < meet.date) = 0 " +
                    "AND  meet.date + interval '1' day * (SELECT remind FROM public.client cli3 WHERE cli3.id_client = cli.id_client) < CURRENT_TIMESTAMP " +
                    "AND meet.date <= CURRENT_TIMESTAMP " +
                    "AND NOT EXISTS(SELECT id_merchant, id_client, id_meeting FROM public.notification noti " +
                    "   WHERE noti.id_meeting = meet.id_meeting AND noti.id_merchant = mer.id_merchant AND noti.id_client = cli.id_client) ",
            nativeQuery = true
    )
    List<Long[]> getNotificationsToSend();

    Page<Meeting> findByMatterContains(String matter, Pageable pageable);
    long countByMatterContains(String matter);
}
