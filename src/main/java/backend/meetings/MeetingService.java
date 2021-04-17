package backend.meetings;

import backend.api.meetings.*;
import backend.filemanagment.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MeetingService {
    MeetingResponse getMeetingById(Long idMeeting);
    MeetingPaginatedResponse getMeetings(Integer pageNumber, Integer pageSize);
    MeetingPaginatedResponse getMeetingsByMatter(String matter, Integer pageNumber, Integer pageSize);
    MeetingWordCloudResponse getMeetingWordCloudById(Long idMeeting);
    File getMeetingFileById(Long idMeeting, Long idFile);
    MeetingResponse registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest);
    void modifyMeetingDate(Long meetingId, MeetingDateChangeRequest meetingDateChangeRequest);
    void modifyMeetingMatter(Long meetingId, MeetingMatterChangeRequest meetingMatterChangeRequest);
    void modifyMeetingDescription(Long meetingId, MeetingDescriptionChangeRequest meetingDescriptionChangeRequest);
    void addMeetingMerchant(Long meetingId, MeetingSubjectChangeRequest meetingSubjectChangeRequest);
    void addMeetingClient(Long meetingId, MeetingSubjectChangeRequest meetingSubjectChangeRequest);
    void addMeetingKeyword(Long meetingId, MeetingKeywordChangeRequest meetingKeywordChangeRequest);
    MeetingFileResponse addMeetingFile(Long meetingId, MultipartFile multipartFile);
    MeetingResponse addMeetingDescriptionFromFile(Long meetingId, Long fileId);
    void deleteMeetingMerchant(Long meetingId, Long merchantId);
    void deleteMeetingClient(Long meetingId, Long clientId);
    void deleteMeetingKeyword(Long meetingId, String keyword);
    void deleteMeetingFile(Long meetingId, Long fileId);
    void deleteMeeting(Long meetingId);
}
