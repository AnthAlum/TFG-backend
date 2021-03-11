package backend.meetings;

import backend.api.meetings.MeetingRegistrationRequest;
import backend.api.meetings.MeetingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeetingMapper {
    @Mapping(target = "merchants", ignore = true)
    @Mapping(target = "clients", ignore = true)
    MeetingResponse meetingToMeetingResponse(Meeting meeting);

    @Mapping(target = "idMeeting", ignore = true)
    @Mapping(target = "merchants", ignore = true)
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    Meeting meetingRegistrationRequestToMeeting(MeetingRegistrationRequest meetingRegistrationRequest);
}
