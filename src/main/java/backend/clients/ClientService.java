package backend.clients;

import backend.api.clients.*;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    public Client findClientById(Long idClient);
    public ClientResponse getClientById(Long idClient);
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest);
    public ClientListResponse getClients();
    public void deleteClient(Long idClient);
    public void modifyClientName(ClientNameChangeRequest clientNameChangeRequest, Long idClient);
    public void modifyClientEmail(ClientEmailChangeRequest clientEmailChangeRequest, Long idClient);
    public void  modifyClientPhone(ClientPhoneChangeRequest clientPhoneChangeRequest, Long idClient);
    public void modifyClientCompany(ClientCompanyChangeRequest clientCompanyChangeRequest, Long idClient);
}
