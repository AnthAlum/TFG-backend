package backend.filemanagment;

import backend.api.meetings.MeetingFileResponse;
import backend.meetings.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;
    private FileMapper fileMapper;

    private static final Logger LOGGER = Logger.getLogger( FileServiceImpl.class.getName() );

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
        if(filename == null)
            return null;
        int index = filename.lastIndexOf('.');
        String extension = filename.substring(index + 1);
        try {
            File file = fileRepository.save(new File(null, filename, extension, multipartFile.getBytes(), meeting));
            meeting.addFile(file);
            meetingFileResponse = fileMapper.fileToMeetingFileResponse(file);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
        return meetingFileResponse;
    }

    @Override
    @Transactional
    public void deleteFile(Meeting meeting, Long fileId) {
        File file = fileRepository.findById(fileId).orElse(null);
        if(file == null)
            return;
        meeting.removeFile(file);
        fileRepository.delete(file);
    }
}
