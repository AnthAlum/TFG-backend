package backend.meetings;

import backend.api.clients.ClientResponse;
import backend.api.meetings.MeetingListResponse;
import backend.api.meetings.MeetingPaginatedResponse;
import backend.api.meetings.MeetingRegistrationRequest;
import backend.api.meetings.MeetingResponse;
import backend.api.merchants.MerchantResponse;
import backend.api.others.PaginationInfo;
import backend.clients.ClientRepository;
import backend.clients.ClientService;
import backend.merchants.MerchantRepository;
import backend.merchants.MerchantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MeetingServiceImpl implements MeetingService{
    private MeetingRepository meetingRepository;
    private MeetingMapper meetingMapper;
    private MerchantRepository merchantRepository;
    private MerchantsService merchantService;
    private ClientRepository clientRepository;
    private ClientService clientService;

    @Autowired
    public MeetingServiceImpl(MeetingRepository meetingRepository,
                              MeetingMapper meetingMapper,
                              MerchantRepository merchantRepository,
                              MerchantsService merchantService,
                              ClientRepository clientRepository,
                              ClientService clientService) {
        super();
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
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
        meetingPage.forEach(meeting -> { //1: For each meeting
            MeetingResponse meetingResponse = meetingMapper.meetingToMeetingResponse(meeting); //  create the meetingResponse
            meeting.getMerchants().forEach(merchant ->{  // 1.1: For each merchant asociated to the given meeting
                MerchantResponse merchantResponse = merchantService.getMerchantById(merchant.getIdMerchant()); // create the merchantResponse
                meetingResponse.addMerchantResponse(merchantResponse); // and add merchantResponse to the list.
            });
            // 1.2: Same for clients.
            meeting.getClients().forEach(client -> {
                ClientResponse clientResponse = clientService.getClientById(client.getIdClient());
                meetingResponse.addClientResponse(clientResponse);
            });
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

    @Override
    public void deleteMeeting(Long meetingId) {
        meetingRepository.delete(meetingRepository.findById(meetingId).orElse(null));
    }

    @Override
    public MeetingResponse getMeetingById(Long idMeeting) {
        Meeting meeting = meetingRepository.findById(idMeeting).orElse(null);
        MeetingResponse meetingResponse = meetingMapper.meetingToMeetingResponse(meeting);
        meeting.getMerchants().forEach(merchant -> {
            MerchantResponse merchantResponse = merchantService.getMerchantById(merchant.getIdMerchant());
            meetingResponse.addMerchantResponse(merchantResponse);
        });
        return meetingResponse;
    }
}
