package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MeetingFileListResponse {
    @JsonProperty("files")
    private List<MeetingFileResponse> meetingFileResponseList;

    public MeetingFileListResponse() {
        this.meetingFileResponseList = new ArrayList<>();
    }

    public MeetingFileListResponse(List<MeetingFileResponse> meetingFileResponseList) {
        this.meetingFileResponseList = meetingFileResponseList;
    }

    public List<MeetingFileResponse> getMeetingFileResponseList() {
        return meetingFileResponseList;
    }

    public void setMeetingFileResponseList(List<MeetingFileResponse> meetingFileResponseList) {
        this.meetingFileResponseList = meetingFileResponseList;
    }

    public void addFileResponse(MeetingFileResponse meetingFileResponse){
        this.meetingFileResponseList.add(meetingFileResponse);
    }
}
