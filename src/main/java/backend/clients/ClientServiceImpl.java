package backend.clients;

import backend.api.clients.*;
import backend.api.meetings.MeetingResponse;
import backend.api.others.PaginationInfo;
import backend.meetings.MeetingRepository;
import backend.meetings.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientMapper clientMapper;
    private ClientRepository clientRepository;
    private MeetingRepository meetingRepository;
    private MeetingService meetingService;

    public ClientServiceImpl() {
    }

    @Autowired
    public ClientServiceImpl(ClientMapper clientMapper,
                             ClientRepository clientRepository,
                             MeetingRepository meetingRepository,
                             MeetingService meetingService) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.meetingRepository = meetingRepository;
        this.meetingService = meetingService;
    }

    @Override
    public ClientResponse getClientById(Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        ClientResponse clientResponse = clientMapper.ClientToClientResponse(client);
        return clientResponse;
    }

    @Override
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest) {
        Client client = clientMapper.clientRegistrationRequestToClient(clientRegistrationRequest);
        clientRepository.save(client);
    }

    @Override
    public ClientPaginatedResponse getClients(Integer pageNumber, Integer pageSize){
        Page<Client> clientPage = clientRepository.searchClients(PageRequest.of(pageNumber, pageSize));
        return buildResponse(clientPage, (int)clientRepository.count());
    }

    @Override
    public ClientPaginatedResponse getClientsByEmail(String email, Integer pageNumber, Integer pageSize) {
        Page<Client> clientPage = clientRepository.findByEmailContains(email, PageRequest.of(pageNumber, pageSize));
        return buildResponse(clientPage, (int)clientRepository.countByEmailContains(email));
    }

    @Override
    public ClientPaginatedResponse getClientsByName(String name, Integer pageNumber, Integer pageSize) {
        Page<Client> clientPage = clientRepository.findByNameContains(name, PageRequest.of(pageNumber, pageSize));
        return buildResponse(clientPage, (int)clientRepository.countByNameContains(name));
    }

    @Override
    public ClientPaginatedResponse getClientsByPhone(String phone, Integer pageNumber, Integer pageSize) {
        Page<Client> clientPage = clientRepository.findByPhoneContains(phone, PageRequest.of(pageNumber, pageSize));
        return buildResponse(clientPage, (int)clientRepository.countByPhoneContains(phone));
    }

    @Override
    public ClientPaginatedResponse getClientsByCompany(String company, Integer pageNumber, Integer pageSize) {
        Page<Client> clientPage = clientRepository.findByCompanyContains(company, PageRequest.of(pageNumber, pageSize));
        return buildResponse(clientPage, (int)clientRepository.countByCompanyContains(company));
    }

    private ClientPaginatedResponse buildResponse(Page<Client> clientPage, int totalElements){
        if(clientPage == null)
            return null;
        ClientListResponse clientListResponse = new ClientListResponse(); //Create the list of clientResponses for the
        clientPage.forEach(client -> {
            ClientResponse clientResponse = clientMapper.ClientToClientResponse(client);
            client.getMeetings().forEach(meeting -> {
                MeetingResponse meetingResponse = meetingService.getMeetingById(meeting.getIdMeeting());
                clientResponse.addMeetingResponse(meetingResponse);
            });
            clientListResponse.addClientResponse(clientResponse);
        });

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setTotalElements(totalElements);
        paginationInfo.setTotalPages(clientPage.getTotalPages());

        ClientPaginatedResponse clientPaginatedResponse = new ClientPaginatedResponse();
        clientPaginatedResponse.setPages(clientListResponse.getClientResponseList());
        clientPaginatedResponse.setPaginationInfo(paginationInfo);
        return clientPaginatedResponse;
    }

    @Override
    public void deleteClient(Long idClient){
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            clientRepository.delete(client);
        }
    }

    @Override
    public void modifyClientName(ClientNameChangeRequest clientNameChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setName(clientNameChangeRequest.getNewName());
            clientRepository.save(client);
        }
    }

    @Override
    public void modifyClientEmail(ClientEmailChangeRequest clientEmailChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setEmail(clientEmailChangeRequest.getNewEmail());
            clientRepository.save(client);
        }
    }

    @Override
    public void modifyClientPhone(ClientPhoneChangeRequest clientPhoneChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setPhone(clientPhoneChangeRequest.getNewPhone());
            clientRepository.save(client);
        }
    }

    @Override
    public void modifyClientCompany(ClientCompanyChangeRequest clientCompanyChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setCompany(clientCompanyChangeRequest.getNewCompany());
            clientRepository.save(client);
        }
    }

}
