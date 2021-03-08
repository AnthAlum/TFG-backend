package backend.meetings;

import backend.api.meetings.MeetingListResponse;
import backend.api.meetings.MeetingPaginatedResponse;
import backend.api.meetings.MeetingResponse;
import backend.api.others.PaginationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class MeetingServiceImpl implements MeetingService{
    private MeetingRepository meetingRepository;
    private MeetingMapper meetingMapper;
    @Override
    public MeetingPaginatedResponse getMeeting(Integer pageNumber, Integer pageSize) {
        Page<Meeting> meetingPage = meetingRepository.searchMeetings(PageRequest.of(pageNumber, pageSize));
        return buildResponse(meetingPage, (int)meetingRepository.count());
    }

    private MeetingPaginatedResponse buildResponse(Page<Meeting> meetingPage, int totalElements){
        if(meetingPage == null) {
            return null;
        }
        MeetingListResponse meetingListResponse = new MeetingListResponse();
        meetingPage.forEach(meeting -> {
            MeetingResponse meetingResponse = meetingMapper.meetingToMeetingResponse(meeting);
            meetingResponse.setIdMerchant(meeting.getMerchant().getIdMerchant());
            meetingResponse.setIdClient(meeting.getClient().getIdClient());
            meetingListResponse.addMeetingResponse(meetingResponse);
        });
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setTotalPages(meetingPage.getTotalPages());
        paginationInfo.setTotalElements(totalElements);

        MeetingPaginatedResponse meetingPaginatedResponse = new MeetingPaginatedResponse();
        meetingPaginatedResponse.setPages(meetingListResponse.getMeetingResponseList());
        meetingPaginatedResponse.setPaginationInfo(paginationInfo);
        return meetingPaginatedResponse;
    }

}
