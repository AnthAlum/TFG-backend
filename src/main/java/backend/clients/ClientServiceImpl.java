package backend.clients;

import backend.api.clients.ClientListResponse;
import backend.api.clients.ClientRegistrationRequest;
import backend.api.clients.ClientResponse;
import backend.api.merchants.MerchantListResponse;
import backend.api.merchants.MerchantResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ClientListResponse getClients(){
        List<Client> clientList = (List<Client>)clientRepository.findAll(); //Obtener todos los clients
        ClientListResponse clientListResponse = new ClientListResponse(); //Crear la lista de responses.
        clientList.forEach(client ->
                clientListResponse.addClientResponse(
                        clientMapper.ClientToClientResponse(client)
                )); //Mapear todos los clients a clientsResponse y guardalos en la lista.
        return clientListResponse;
    }

    @Override
    public void deleteClient(Long idClient){
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client != null){
            clientRepository.delete(client);
        }
    }

    @Override
    public void modifyClient(Long idClient){

    }
}
