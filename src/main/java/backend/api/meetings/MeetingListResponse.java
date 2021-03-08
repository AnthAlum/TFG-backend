package backend.api.meetings;

import java.util.ArrayList;
import java.util.List;

public class MeetingListResponse {
    private List<MeetingResponse> meetingResponseList;

    public MeetingListResponse() {
        this.meetingResponseList = new ArrayList<>();
    }

    public MeetingListResponse(List<MeetingResponse> meetingResponseList) {
        this.meetingResponseList = meetingResponseList;
    }

    public List<MeetingResponse> getMeetingResponseList() {
        return meetingResponseList;
    }

    public void setMeetingResponseList(List<MeetingResponse> meetingResponseList) {
        this.meetingResponseList = meetingResponseList;
    }

    public void addMeetingResponse(MeetingResponse meetingResponse){
        this.meetingResponseList.add(meetingResponse);
    }
}
