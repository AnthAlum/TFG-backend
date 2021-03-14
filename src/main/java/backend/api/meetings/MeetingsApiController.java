package backend.api.meetings;

import backend.meetings.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MeetingsApiController implements MeetingsApi{
    private MeetingService meetingService;

    @Autowired
    public MeetingsApiController(MeetingService meetingService){
        this.meetingService = meetingService;
    }

    public ResponseEntity<MeetingPaginatedResponse> getMeetings(Integer pageNumber, Integer size) {
        MeetingPaginatedResponse response = meetingService.getMeeting(pageNumber, size);
        return checkResponse(response);
    }

    private ResponseEntity<MeetingPaginatedResponse> checkResponse(MeetingPaginatedResponse meetingPaginatedResponse){
        if(meetingPaginatedResponse == null)
            return new ResponseEntity<MeetingPaginatedResponse>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MeetingPaginatedResponse>(meetingPaginatedResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> registerMeeting(@Valid MeetingRegistrationRequest meetingRegistrationRequest) {
        meetingService.registerMeeting(meetingRegistrationRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteMeeting(Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyDate(Long meetingId, @Valid MeetingDateChangeRequest meetingDateChangeRequest) {
        meetingService.modifyMeetingDate(meetingId, meetingDateChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyMatter(Long meetingId, @Valid MeetingMatterChangeRequest meetingMatterChangeRequest) {
        meetingService.modifyMeetingMatter(meetingId, meetingMatterChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyMerchant(Long meetingId, @Valid MeetingMerchantChangeRequest meetingMerchantChangeRequest) {
        meetingService.modifyMeetingMerchant(meetingId, meetingMerchantChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyClient(Long meetingId, @Valid MeetingClientChangeRequest meetingClientChangeRequest) {
        meetingService.modifyMeetingClient(meetingId, meetingClientChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
