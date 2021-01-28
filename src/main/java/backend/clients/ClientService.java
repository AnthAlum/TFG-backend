package backend.clients;

import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    public Client findUserById(Long idCliente);
    public ClientResponse getClientById(Long idClient);
    public void registerClient(ClientRegistrationRequest clientRegistrationRequest);
}
