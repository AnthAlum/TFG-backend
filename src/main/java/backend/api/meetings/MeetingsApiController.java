package backend.api.meetings;

import backend.meetings.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MeetingsApiController {
    private MeetingService meetingService;

    @Autowired
    public MeetingsApiController(MeetingService meetingService){
        this.meetingService = meetingService;
    }

    public ResponseEntity<MeetingPaginatedResponse> getClients(Integer pageNumber, Integer size) {
        MeetingPaginatedResponse response = meetingService.getMeeting(pageNumber, size);
        return checkResponse(response);
    }

    private ResponseEntity<MeetingPaginatedResponse> checkResponse(MeetingPaginatedResponse meetingPaginatedResponse){
        if(meetingPaginatedResponse == null)
            return new ResponseEntity<MeetingPaginatedResponse>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MeetingPaginatedResponse>(meetingPaginatedResponse, HttpStatus.OK);
    }
}
