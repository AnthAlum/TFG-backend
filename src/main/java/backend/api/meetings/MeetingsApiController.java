package backend.api.meetings;

import backend.filemanagment.File;
import backend.meetings.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(meetingPaginatedResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MeetingResponse> getMeeting(Long meetingId) {
        MeetingResponse response = meetingService.getMeetingById(meetingId);
        if(response == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MeetingWordCloudResponse> getWordCloudById(Long meetingId) {
        MeetingWordCloudResponse cloud = meetingService.getMeetingWordCloudById(meetingId);
        return new ResponseEntity<>(cloud, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> getFileById(Long meetingId, Long fileId) {
        File file = meetingService.getMeetingFileById(meetingId, fileId);
        if(file != null) {
            ByteArrayResource resource = new ByteArrayResource(file.getData());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MeetingFileResponse> addFile(Long meetingId, MultipartFile file) {
        return new ResponseEntity<>(meetingService.addMeetingFile(meetingId, file), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MeetingResponse> addMeetingDescriptionFromFile(Long meetingId, Long fileId) {
        return new ResponseEntity<>(meetingService.addMeetingDescriptionFromFile(meetingId, fileId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteMeeting(Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteFile(Long meetingId, Long fileId) {
        meetingService.deleteMeetingFile(meetingId, fileId);
        return new ResponseEntity<>(HttpStatus.OK);
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
