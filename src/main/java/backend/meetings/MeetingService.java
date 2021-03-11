package backend.meetings;

import backend.api.meetings.MeetingPaginatedResponse;
import backend.api.meetings.MeetingRegistrationRequest;
import backend.api.meetings.MeetingResponse;
import org.springframework.stereotype.Service;

@Service
public interface MeetingService {
    public MeetingResponse getMeetingById(Long idMeeting);
    public MeetingPaginatedResponse getMeeting(Integer pageNumber, Integer pageSize);
    public void registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest);
    public void deleteMeeting(Long meetingId);
}
