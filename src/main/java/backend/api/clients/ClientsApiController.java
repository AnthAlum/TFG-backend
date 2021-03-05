package backend.api.clients;

import backend.clients.ClientService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")
@RestController
public class ClientsApiController implements ClientsApi {

    private static final Logger log = LoggerFactory.getLogger(ClientsApiController.class);

    private ClientService clientService;

    @org.springframework.beans.factory.annotation.Autowired
    public ClientsApiController(ClientService clientService) {
        this.clientService = clientService;
    }

    public ResponseEntity<Void> createClient(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ClientRegistrationRequest body) {
        clientService.registerClient(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteClient(@Parameter(in = ParameterIn.PATH, description = "Client's ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<ClientResponse> getClient(@Parameter(in = ParameterIn.PATH, description = "Client's ID.", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId) {
        return new ResponseEntity<ClientResponse>(clientService.getClientById(clientId), HttpStatus.OK);
    }

    public ResponseEntity<ClientPaginatedResponse> getClients(Integer pageNumber, Integer size) {
        ClientPaginatedResponse clients = clientService.getClients(pageNumber, size);
        if(clients == null)
            return new ResponseEntity<ClientPaginatedResponse>(clients, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ClientPaginatedResponse>(clients, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyClientName(Long clientId, @Valid ClientNameChangeRequest body) {
        clientService.modifyClientName(body, clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyClientEmail(Long clientId, @Valid ClientEmailChangeRequest body) {
        clientService.modifyClientEmail(body, clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyClientPhone(Long clientId, @Valid ClientPhoneChangeRequest body) {
        clientService.modifyClientPhone(body, clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyClientCompany(Long clientId, @Valid ClientCompanyChangeRequest body) {
        clientService.modifyClientCompany(body, clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
