package backend.filemanagment;

import backend.api.meetings.MeetingFileResponse;
import backend.meetings.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;
    private FileMapper fileMapper;

    public FileRepository getFileRepository() {
        return fileRepository;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileMapper getFileMapper() {
        return fileMapper;
    }

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    @Transactional
    public MeetingFileResponse getFileById(Long idFile) {
        File file = fileRepository.findById(idFile).orElse(null);
        return fileMapper.fileToMeetingFileResponse(file);
    }

    @Override
    @Transactional
    public MeetingFileResponse postFile(Meeting meeting, MultipartFile multipartFile) {
        MeetingFileResponse meetingFileResponse = null;
        String filename = multipartFile.getOriginalFilename();
        int index = filename.lastIndexOf('.');
        String extension = filename.substring(index + 1);
        try {
            File file = fileRepository.save(new File(null, filename, extension, multipartFile.getBytes(), meeting));
            meeting.addFile(file);
            meetingFileResponse = fileMapper.fileToMeetingFileResponse(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meetingFileResponse;
    }

    @Override
    @Transactional
    public void deleteFile(Meeting meeting, Long fileId) {
        File file = fileRepository.findById(fileId).orElse(null);
        meeting.removeFile(file);
        fileRepository.delete(file);
    }
}
