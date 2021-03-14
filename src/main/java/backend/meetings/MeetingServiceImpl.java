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

import javax.transaction.Transactional;

@Service
public class MeetingServiceImpl implements MeetingService{
    private MeetingRepository meetingRepository;
    private MeetingMapper meetingMapper;
    private MerchantRepository merchantRepository;
    private MerchantsService merchantService;
    private ClientRepository clientRepository;
    private ClientService clientService;
/*
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
*/

    public MeetingRepository getMeetingRepository() {
        return meetingRepository;
    }

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingMapper getMeetingMapper() {
        return meetingMapper;
    }

    @Autowired
    public void setMeetingMapper(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    public MerchantRepository getMerchantRepository() {
        return merchantRepository;
    }

    @Autowired
    public void setMerchantRepository(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public MerchantsService getMerchantService() {
        return merchantService;
    }

    @Autowired
    public void setMerchantService(MerchantsService merchantService) {
        this.merchantService = merchantService;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientService getClientService() {
        return clientService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    @Transactional
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
            meetingListResponse.addMeetingResponse(meetingResponse); //Add the meetingResponse to the list of meetingResponses.
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
    @Transactional
    public void registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest) {
        Meeting meeting = meetingMapper.meetingRegistrationRequestToMeeting(meetingRegistrationRequest); //Obtenemos el meeting.
        meetingRegistrationRequest.getMerchants().forEach(idMerchant -> // 1. Con cada idMerchant del registrationRequest
                meeting.addMerchant(merchantRepository.findById(idMerchant).orElse(null)) //    buscamos el merchant y lo guardamos en la lista.
        );
        // Igual con los clientes.
        meetingRegistrationRequest.getClients().forEach(idClient ->
                meeting.addClient(clientRepository.findById(idClient).orElse(null))
        );
        meeting.setDate(meetingRegistrationRequest.getLocalDateTime());
        meetingRepository.save(meeting);
        meeting.getMerchants().forEach(merchant -> merchant.addMeeting(meeting)); // Guardamos el nuevo meeting en las listas de los merchants.
        meeting.getClients().forEach(client -> client.addMeeting(meeting)); // Igual con los clientes.
    }

    @Override
    @Transactional
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        meeting.getMerchants().forEach(merchant -> { // Por cada merchant relacionado con el meeting
            merchant.deleteMeeting(meeting);    // quitamos el meeting a eliminar.
            merchantRepository.save(merchant);  //   y actualizamos la lista de los meetings.
        });
        meeting.getClients().forEach(client -> { // Igual en los clientes.
            client.deleteMeeting(meeting);
            clientRepository.save(client);
        });
        meetingRepository.delete(meeting);
    }

    @Override
    @Transactional
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
