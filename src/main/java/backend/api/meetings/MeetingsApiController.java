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

    private ResponseEntity<MeetingPaginatedResponse> checkResponse(MeetingPaginatedResponse meetingPaginatedResponse){
        if(meetingPaginatedResponse == null)
            return new ResponseEntity<MeetingPaginatedResponse>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MeetingPaginatedResponse>(meetingPaginatedResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MeetingResponse> getMeeting(Long meetingId) {
        MeetingResponse response = meetingService.getMeetingById(meetingId);
        if(response == null)
            return new ResponseEntity<MeetingResponse>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MeetingResponse>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MeetingPaginatedResponse> getMeetings(Integer pageNumber, Integer size) {
        MeetingPaginatedResponse response = meetingService.getMeetings(pageNumber, size);
        return checkResponse(response);
    }

    @Override
    public ResponseEntity<MeetingPaginatedResponse> getMeetingsByMatter(@Valid String matter, @Valid Integer pageNumber, @Valid Integer pageSize) {
        MeetingPaginatedResponse response = meetingService.getMeetingsByMatter(matter, pageNumber, pageSize);
        return checkResponse(response);
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
    public ResponseEntity<Void> modifyDescription(Long meetingId, @Valid MeetingDescriptionChangeRequest meetingDescriptionChangeRequest) {
        meetingService.modifyMeetingDescription(meetingId, meetingDescriptionChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addMerchant(Long meetingId, @Valid MeetingSubjectChangeRequest meetingSubjectChangeRequest) {
        meetingService.addMeetingMerchant(meetingId, meetingSubjectChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addClient(Long meetingId, @Valid MeetingSubjectChangeRequest meetingSubjectChangeRequest) {
        meetingService.addMeetingClient(meetingId, meetingSubjectChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addKeyword(Long meetingId, @Valid MeetingKeywordChangeRequest meetingKeywordChangeRequest) {
        meetingService.addMeetingKeyword(meetingId, meetingKeywordChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteMerchant(Long meetingId, Long merchantId) {
        meetingService.deleteMeetingMerchant(meetingId, merchantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteClient(Long meetingId, Long clientId) {
        meetingService.deleteMeetingClient(meetingId, clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteKeyword(Long meetingId, String keyword) {
        meetingService.deleteMeetingKeyword(meetingId, keyword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
