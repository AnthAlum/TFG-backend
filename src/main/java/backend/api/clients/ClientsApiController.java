package backend.api.clients;

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

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ClientsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createClient(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Cliente body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteClient(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> getClient(@Parameter(in = ParameterIn.PATH, description = "El ID del cliente.", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ListaClientes> getClients() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ListaClientes>(objectMapper.readValue("{\r\n  \"clientes\" : [ {\r\n    \"id\" : 67890,\r\n    \"telefono\" : \"987654321\",\r\n    \"empresa\" : \"Cinco Jotas\",\r\n    \"nombre\" : \"Fernando Carlos Roca Rivas\",\r\n    \"idComerciante\" : 0,\r\n    \"email\" : \"correoexample@example.com\"\r\n  }, {\r\n    \"id\" : 67890,\r\n    \"telefono\" : \"987654321\",\r\n    \"empresa\" : \"Cinco Jotas\",\r\n    \"nombre\" : \"Fernando Carlos Roca Rivas\",\r\n    \"idComerciante\" : 0,\r\n    \"email\" : \"correoexample@example.com\"\r\n  } ]\r\n}", ListaClientes.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ListaClientes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ListaClientes>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> modifyClient(@Parameter(in = ParameterIn.PATH, description = "El ID del cliente a modificar", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Cliente body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
