package backend.filemanagment;

import backend.api.meetings.MeetingFileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    MeetingFileResponse fileToMeetingFileResponse(File file);
}
