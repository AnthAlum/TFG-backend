package backend.meetings;

import backend.api.meetings.MeetingPaginatedResponse;

public interface MeetingService {
    public MeetingPaginatedResponse getMeeting(Integer pageNumber, Integer pageSize);
}
