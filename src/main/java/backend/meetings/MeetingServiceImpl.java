package backend.meetings;

import backend.api.meetings.MeetingListResponse;
import backend.api.meetings.MeetingPaginatedResponse;
import backend.api.meetings.MeetingRegistrationRequest;
import backend.api.meetings.MeetingResponse;
import backend.api.others.PaginationInfo;
import backend.clients.ClientRepository;
import backend.merchants.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MeetingServiceImpl implements MeetingService{
    private MeetingRepository meetingRepository;
    private MeetingMapper meetingMapper;
    private MerchantRepository merchantRepository;
    private ClientRepository clientRepository;

    @Autowired
    public MeetingServiceImpl(MeetingRepository meetingRepository, MeetingMapper meetingMapper, MerchantRepository merchantRepository, ClientRepository clientRepository) {
        super();
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
        this.merchantRepository = merchantRepository;
        this.clientRepository = clientRepository;
    }

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
            meetingResponse.setIdsMerchant(meeting.getMerchantsIds());
            meetingResponse.setIdsClient(meeting.getClientsIds());
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

    @Override
    public void registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest) {
        Meeting meeting = meetingMapper.meetingRegistrationRequestToMeeting(meetingRegistrationRequest);
        meetingRegistrationRequest.getMerchants().forEach(idMerchant ->
                meeting.addMerchant(merchantRepository.findById(idMerchant).orElse(null))
        );
        meetingRegistrationRequest.getClients().forEach(idClient ->
                meeting.addClient(clientRepository.findById(idClient).orElse(null))
        );
        meeting.setDate(meetingRegistrationRequest.getLocalDateTime());
        meetingRepository.save(meeting);
    }
}
