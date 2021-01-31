package backend.clients;

import backend.api.clients.ClientListResponse;
import backend.api.clients.ClientRegistrationRequest;
import backend.api.clients.ClientResponse;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    public Client findClientById(Long idClient);
    public ClientResponse getClientById(Long idClient);
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest);
    public ClientListResponse getClients();
    public void deleteClient(Long idClient);
    public void modifyClient(Long idClient);
}
