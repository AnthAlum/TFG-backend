package backend.filemanagment;

import backend.api.meetings.MeetingFileResponse;
import backend.meetings.Meeting;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    MeetingFileResponse getFileById(Long idFile);
    MeetingFileResponse postFile(Meeting meeting, MultipartFile multipartFile);
    void deleteFile(Meeting meeting, Long fileId);
}
