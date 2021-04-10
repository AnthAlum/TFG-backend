package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class MeetingSimplifiedListResponse {

    @JsonProperty("simplifiedList")
    private List<MeetingSimplifiedResponse> meetingSimplifiedResponseList = new ArrayList<>();

    public MeetingSimplifiedListResponse() {
    }

    public MeetingSimplifiedListResponse(List<MeetingSimplifiedResponse> meetingSimplifiedResponseList) {
        this.meetingSimplifiedResponseList = meetingSimplifiedResponseList;
    }

    public List<MeetingSimplifiedResponse> getMeetingSimplifiedResponseList() {
        return meetingSimplifiedResponseList;
    }

    public void setMeetingSimplifiedResponseList(List<MeetingSimplifiedResponse> meetingSimplifiedResponseList) {
        this.meetingSimplifiedResponseList = meetingSimplifiedResponseList;
    }

    public void addResponse(MeetingSimplifiedResponse meetingSimplifiedResponse){
        this.meetingSimplifiedResponseList.add(meetingSimplifiedResponse);
    }
}
