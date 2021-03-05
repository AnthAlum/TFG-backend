package backend.clients;

import backend.api.clients.*;
import backend.api.merchants.MerchantPaginatedResponse;
import backend.api.others.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientMapper clientMapper;
    private ClientRepository clientRepository;

    public ClientServiceImpl() {
    }

    @Autowired
    public ClientServiceImpl(ClientMapper clientMapper, ClientRepository clientRepository) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client findClientById(Long idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        return client;
    }

    @Override
    public ClientResponse getClientById(Long idClient) {
        Client client = findClientById(idClient);
        ClientResponse clientResponse = clientMapper.ClientToClientResponse(client);
        return clientResponse;
    }

    @Override
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest) {
        Client client = clientMapper.clientRegistrationRequestToClient(clientRegistrationRequest);
        clientRepository.save(client);
    }

    @Override
    public ClientPaginatedResponse getClients(Integer pageNumber, Integer size){
        Page<Client> clientPage = clientRepository.searchClients(PageRequest.of(pageNumber, size));
        return buildResponse(clientPage, (int)clientRepository.count());
    }

    private ClientPaginatedResponse buildResponse(Page<Client> clientPage, int totalElements){
        if(clientPage == null)
            return null;
        ClientListResponse clientListResponse = new ClientListResponse();
        clientPage.forEach(client ->
                clientListResponse.addClientResponse(
                        clientMapper.ClientToClientResponse(client)
                ));
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
            client.setName(clientEmailChangeRequest.getNewEmail());
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
            client.setName(clientCompanyChangeRequest.getNewCompany());
            clientRepository.save(client);
        }
    }

}
