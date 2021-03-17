package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MeetingListResponse {
    @JsonProperty("meetingResponseList")
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
