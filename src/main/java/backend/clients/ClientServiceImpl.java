package backend.clients;

import backend.api.clients.ClientRegistrationRequest;
import backend.api.clients.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;

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
    public Client findClientById(Long idCliente) {
        Client client = clientRepository.findById(idCliente).orElse(null);
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
}
