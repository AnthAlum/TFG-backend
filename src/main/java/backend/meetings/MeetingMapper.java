package backend.meetings;

import backend.api.meetings.MeetingRegistrationRequest;
import backend.api.meetings.MeetingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeetingMapper {
    @Mapping(target = "idMerchant", ignore = true)
    @Mapping(target = "idClient", ignore = true)
    MeetingResponse meetingToMeetingResponse(Meeting meeting);

    @Mapping(target = "idMeeting", ignore = true)
    @Mapping(target = "merchant", ignore = true)
    @Mapping(target = "client", ignore = true)
    Meeting meetingRegistrationRequestToMeeting(MeetingRegistrationRequest meetingRegistrationRequest);
}
