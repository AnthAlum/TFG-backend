package backend.api.clients;

import backend.clients.ClientService;
import backend.clients.ClientServiceImpl;
import io.swagger.model.Cliente;
import io.swagger.model.ListaClientes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public ResponseEntity<ClientListResponse> getClients() {
        return new ResponseEntity<ClientListResponse>(clientService.getClients(), HttpStatus.OK);
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
