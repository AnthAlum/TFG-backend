/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.24).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package backend.api.clients;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")
public interface ClientsApi {

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Creates a client.", description = "Creates a new client with the given information in the request.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Client created successfully."),
        
        @ApiResponse(responseCode = "400", description = "The request has an invalid input.") })
    @RequestMapping(value = "/clients",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> createClient(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ClientRegistrationRequest body);


    @Operation(summary = "Deletes a client.", description = "Deletes a client of the given ID.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Client deleted successfully."),
        
        @ApiResponse(responseCode = "404", description = "Client not found with the given ID.") })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteClient(@Parameter(in = ParameterIn.PATH, description = "Client ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId);


    @Operation(summary = "Returns client's information.", description = "Returns client's information of the given ID.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Request completed."),
        
        @ApiResponse(responseCode = "404", description = "Client not found with the given ID.") })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.GET)
    ResponseEntity<ClientResponse> getClient(@Parameter(in = ParameterIn.PATH, description = "Client's ID.", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId);


    @Operation(summary = "Returns all clients information.", description = "Returns all clients information in a list.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = ClientPaginatedResponse.class))) })
    @RequestMapping(value = "/clients",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ClientPaginatedResponse> getClients(@ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
                                                       @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "size", required = false, defaultValue = "25") Integer size);


    @Operation(summary = "Change client's name.", description = "Change client's name with the given new name.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Name updated successfully."),
        
        @ApiResponse(responseCode = "404", description = "Client not found with the given ID.") })
    @RequestMapping(value = "/clients/{clientId}/name",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> modifyClientName(@Parameter(in = ParameterIn.PATH, description = "Client's ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ClientNameChangeRequest body);

    @Operation(summary = "Change client's email.", description = "Change client's email with the given new email.", tags={ "Client" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email updated successfully."),

            @ApiResponse(responseCode = "404", description = "Client not found with the given ID.") })
    @RequestMapping(value = "/clients/{clientId}/email",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyClientEmail(@Parameter(in = ParameterIn.PATH, description = "Client's ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ClientEmailChangeRequest body);

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Change client's phone.", description = "Change client's phone with the given new phone.", tags={ "Client" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone number updated succesfully."),
            @ApiResponse(responseCode = "404", description = "Client not found with the given ID.") })
    @RequestMapping(value = "/clients/{clientId}/phone",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyClientPhone(@Parameter(in = ParameterIn.PATH, description = "Client's ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ClientPhoneChangeRequest body);

    @Operation(summary = "Change client's company.", description = "Change client's company with the given new company.", tags={ "Client" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated successfully."),

            @ApiResponse(responseCode = "404", description = "Client not found with the given ID.") })
    @RequestMapping(value = "/clients/{clientId}/company",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyClientCompany(@Parameter(in = ParameterIn.PATH, description = "Client's ID", required=true, schema=@Schema()) @PathVariable("clientId") Long clientId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ClientCompanyChangeRequest body);

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Returns all clients information that matches with the given email", description = "Returns all clients information that matches with the given email.", tags={ "Client" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = ClientPaginatedResponse.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed for use this method.") })
    @RequestMapping(value = "/clients/findbyemail",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ClientPaginatedResponse> getClientsByEmail(
            @ApiParam(value = "the email for filtering") @Valid @RequestParam(value = "email", required = false, defaultValue = "email@example.com") String email,
            @ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "size", required = false, defaultValue = "25") Integer pageSize);

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Returns all clients information that matches with the given name", description = "Returns all clients information that matches with the given name.", tags={ "Client" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = ClientPaginatedResponse.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed for use this method.") })
    @RequestMapping(value = "/clients/findbyname",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ClientPaginatedResponse> getClientsByName(
            @ApiParam(value = "the email for filtering") @Valid @RequestParam(value = "name", required = false, defaultValue = "name") String name,
            @ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page number", required = false, defaultValue = "0") Integer pageNumber,
            @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "page size", required = false, defaultValue = "25") Integer pageSize);

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Returns all clients information that matches with the given phone", description = "Returns all clients information that matches with the given phone.", tags={ "Client" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = ClientPaginatedResponse.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed for use this method.") })
    @RequestMapping(value = "/clients/findbyphone",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ClientPaginatedResponse> getClientsByPhone(
            @ApiParam(value = "the email for filtering") @Valid @RequestParam(value = "phone", required = false, defaultValue = "phone") String phone,
            @ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page number", required = false, defaultValue = "0") Integer pageNumber,
            @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "page size", required = false, defaultValue = "25") Integer pageSize);

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Returns all clients information that matches with the given company", description = "Returns all clients information that matches with the given company.", tags={ "Client" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = ClientPaginatedResponse.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed for use this method.") })
    @RequestMapping(value = "/clients/findbycompany",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ClientPaginatedResponse> getClientsByCompany(
            @ApiParam(value = "the email for filtering") @Valid @RequestParam(value = "company", required = false, defaultValue = "company") String company,
            @ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page number", required = false, defaultValue = "0") Integer pageNumber,
            @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "page size", required = false, defaultValue = "25") Integer pageSize);

}

