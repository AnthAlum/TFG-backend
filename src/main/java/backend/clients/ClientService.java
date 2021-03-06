package backend.clients;

import backend.api.clients.*;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    public Client findClientById(Long idClient);
    public ClientResponse getClientById(Long idClient);
    public ClientPaginatedResponse getClients(Integer pageNumber, Integer pageSize);
    public ClientPaginatedResponse getClientsByEmail(String email, Integer pageNumber, Integer pageSize);
    public ClientPaginatedResponse getClientsByName(String name, Integer pageNumber, Integer pageSize);
    public ClientPaginatedResponse getClientsByPhone(String phone, Integer pageNumber, Integer pageSize);
    public ClientPaginatedResponse getClientsByCompany(String company, Integer pageNumber, Integer pageSize);
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest);
    public void deleteClient(Long idClient);
    public void modifyClientName(ClientNameChangeRequest clientNameChangeRequest, Long idClient);
    public void modifyClientEmail(ClientEmailChangeRequest clientEmailChangeRequest, Long idClient);
    public void  modifyClientPhone(ClientPhoneChangeRequest clientPhoneChangeRequest, Long idClient);
    public void modifyClientCompany(ClientCompanyChangeRequest clientCompanyChangeRequest, Long idClient);
}
