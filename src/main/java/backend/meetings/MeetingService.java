package backend.meetings;

import backend.api.meetings.MeetingPaginatedResponse;
import backend.api.meetings.MeetingRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public interface MeetingService {
    public MeetingPaginatedResponse getMeeting(Integer pageNumber, Integer pageSize);

    public void registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest);
}
