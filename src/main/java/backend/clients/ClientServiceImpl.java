package backend.clients;

import backend.api.clients.*;
import backend.api.others.PaginationInfo;
import backend.meetings.MeetingRepository;
import backend.meetings.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientMapper clientMapper;
    private ClientRepository clientRepository;
    private MeetingRepository meetingRepository;
    private MeetingService meetingService;

/*
    @Autowired
    public ClientServiceImpl(ClientMapper clientMapper,
                             ClientRepository clientRepository,
                             MeetingRepository meetingRepository,
                             MeetingService meetingService) {
        super();
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.meetingRepository = meetingRepository;
        this.meetingService = meetingService;
    }
*/

    public ClientMapper getClientMapper() {
        return clientMapper;
    }

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public MeetingRepository getMeetingRepository() {
        return meetingRepository;
    }

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingService getMeetingService() {
        return meetingService;
    }

    @Autowired
    public void setMeetingService(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Override
    public ClientResponse getClientById(Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        ClientResponse clientResponse = clientMapper.ClientToClientResponse(client);
        return clientResponse;
    }

    @Override
    @Transactional
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest) {
        Client client = clientMapper.clientRegistrationRequestToClient(clientRegistrationRequest);
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public ClientPaginatedResponse getClients(Integer pageNumber, Integer pageSize){
        Page<Client> clientPage = clientRepository.searchClients(PageRequest.of(pageNumber, pageSize));
        return buildResponse(clientPage, (int)clientRepository.count());
    }

    @Override
    @Transactional
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
    @Transactional
    public ClientPaginatedResponse getClientsByPhone(String phone, Integer pageNumber, Integer pageSize) {
        Page<Client> clientPage = clientRepository.findByPhoneContains(phone, PageRequest.of(pageNumber, pageSize));
        return buildResponse(clientPage, (int)clientRepository.countByPhoneContains(phone));
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteClient(Long idClient){
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            clientRepository.delete(client);
        }
    }

    @Override
    @Transactional
    public void modifyClientName(ClientNameChangeRequest clientNameChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setName(clientNameChangeRequest.getNewName());
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void modifyClientEmail(ClientEmailChangeRequest clientEmailChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setEmail(clientEmailChangeRequest.getNewEmail());
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void modifyClientPhone(ClientPhoneChangeRequest clientPhoneChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setPhone(clientPhoneChangeRequest.getNewPhone());
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void modifyClientCompany(ClientCompanyChangeRequest clientCompanyChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setCompany(clientCompanyChangeRequest.getNewCompany());
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void modifyClientRemind(ClientRemindChangeRequest clientRemindChangeRequest, Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            client.setRemind(clientRemindChangeRequest.getNewRemind());
            clientRepository.save(client);
        }
    }
}
