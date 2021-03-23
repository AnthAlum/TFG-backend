package backend.meetings;

import backend.api.meetings.*;
import org.springframework.stereotype.Service;

@Service
public interface MeetingService {
    MeetingResponse getMeetingById(Long idMeeting);
    MeetingPaginatedResponse getMeetings(Integer pageNumber, Integer pageSize);
    MeetingPaginatedResponse getMeetingsByMatter(String matter, Integer pageNumber, Integer pageSize);
    void registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest);
    void deleteMeeting(Long meetingId);
    void modifyMeetingDate(Long meetingId, MeetingDateChangeRequest meetingDateChangeRequest);
    void modifyMeetingMatter(Long meetingId, MeetingMatterChangeRequest meetingMatterChangeRequest);
    void modifyMeetingDescription(Long meetingId, MeetingDescriptionChangeRequest meetingDescriptionChangeRequest);
    void addMeetingMerchant(Long meetingId, MeetingSubjectChangeRequest meetingSubjectChangeRequest);
    void addMeetingClient(Long meetingId, MeetingSubjectChangeRequest meetingSubjectChangeRequest);
    void addMeetingKeyword(Long meetingId, MeetingKeywordChangeRequest meetingKeywordChangeRequest);
    void deleteMeetingMerchant(Long meetingId, Long merchantId);
    void deleteMeetingClient(Long meetingId, Long clientId);
    void deleteMeetingKeyword(Long meetingId, String keyword);
}
