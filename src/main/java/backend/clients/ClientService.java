package backend.clients;

import backend.api.clients.ClientListResponse;
import backend.api.clients.ClientRegistrationRequest;
import backend.api.clients.ClientResponse;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    public Client findClientById(Long idCliente);
    public ClientResponse getClientById(Long idClient);
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest);
    public ClientListResponse getClients();
}
