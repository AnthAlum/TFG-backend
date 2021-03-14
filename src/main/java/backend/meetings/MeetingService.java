package backend.meetings;

import backend.api.meetings.*;
import org.springframework.stereotype.Service;

@Service
public interface MeetingService {
    public MeetingResponse getMeetingById(Long idMeeting);
    public MeetingPaginatedResponse getMeeting(Integer pageNumber, Integer pageSize);
    public void registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest);
    public void deleteMeeting(Long meetingId);
    public void modifyMeetingDate(Long meetingId, MeetingDateChangeRequest meetingDateChangeRequest);
    public void modifyMeetingMatter(Long meetingId, MeetingMatterChangeRequest meetingMatterChangeRequest);
    public void modifyMeetingMerchant(Long meetingId, MeetingMerchantChangeRequest meetingMerchantChangeRequest);
    public void modifyMeetingClient(Long meetingId, MeetingClientChangeRequest meetingClientChangeRequest);

}
