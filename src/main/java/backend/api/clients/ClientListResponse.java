package backend.api.clients;

import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Validated
public class ClientListResponse {
    private List<ClientResponse> clientResponseList;

    public ClientListResponse(){}

    public ClientListResponse(List<ClientResponse> clientResponseList) {
        this.clientResponseList = clientResponseList;
    }

    public List<ClientResponse> getClientResponseList() {
        return clientResponseList;
    }

    public void setClientResponseList(List<ClientResponse> clientResponseList) {
        this.clientResponseList = clientResponseList;
    }

    public void addClientResponse(ClientResponse clientResponse){
        this.clientResponseList.add(clientResponse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientListResponse that = (ClientListResponse) o;
        return Objects.equals(clientResponseList, that.clientResponseList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientResponseList);
    }

    @Override
    public String toString() {
        return "ClientListResponse{" +
                "clientResponseList=" + clientResponseList +
                '}';
    }
}
