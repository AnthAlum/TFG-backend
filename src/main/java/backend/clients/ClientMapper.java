package backend.clients;

import backend.api.clients.ClientRegistrationRequest;
import backend.api.clients.ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponse ClientToClientResponse(Client client);

    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    Client clientRegistrationRequestToClient(ClientRegistrationRequest clientRegistrationRequest);
}
