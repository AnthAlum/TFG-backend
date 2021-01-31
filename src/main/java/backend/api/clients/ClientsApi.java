/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.24).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package backend.api.clients;

import backend.clients.Client;
import io.swagger.model.Cliente;
import io.swagger.model.ListaClientes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")
public interface ClientsApi {

    @Operation(summary = "Crea un cliente.", description = "Crea un cliente con la informacion recibida.", tags={ "Cliente" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente."),
        
        @ApiResponse(responseCode = "400", description = "Entrada invalida.") })
    @RequestMapping(value = "/clients",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> createClient(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ClientRegistrationRequest body);


    @Operation(summary = "Elimina a un cliente.", description = "Elimina a un cliente.", tags={ "Cliente" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Eliminacion finalizada"),
        
        @ApiResponse(responseCode = "404", description = "El ID indicado no hace referencia a ningun cliente.") })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteClient(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId);


    @Operation(summary = "Obtiene la informacion de un cliente a partir de su ID.", description = "Obtiene la informacion de un cliente a partir de su ID.", tags={ "Cliente" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Consulta completada."),
        
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado.") })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.GET)
    ResponseEntity<ClientResponse> getClient(@Parameter(in = ParameterIn.PATH, description = "El ID del cliente.", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId);


    @Operation(summary = "Devuelve la informacion de todos los clientes.", description = "Devuelve la informacion de todos los clientes.", tags={ "Cliente" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operacion completada", content = @Content(schema = @Schema(implementation = ListaClientes.class))) })
    @RequestMapping(value = "/clients",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ClientListResponse> getClients();


    @Operation(summary = "Cambia la informacion asociada a un cliente a partor de su ID.", description = "Cambia la informacion asociada a un cliente a partor de su ID.", tags={ "Cliente" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Informacion actualizada."),
        
        @ApiResponse(responseCode = "404", description = "El ID indicado no hace referencia a ningun cliente.") })
    @RequestMapping(value = "/clients/{clientId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> modifyClient(@Parameter(in = ParameterIn.PATH, description = "El ID del cliente a modificar", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Cliente body);

}

