package backend.api.meetings;

import backend.api.clients.ClientListResponse;
import backend.api.clients.ClientResponse;
import backend.api.merchants.MerchantListResponse;
import backend.api.merchants.MerchantResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
public class MeetingResponse {
    @JsonProperty("idMeeting")
    private Long idMeeting = null;

    @JsonProperty("matter")
    private String matter = null;

    @JsonProperty("merchants")
    private MerchantListResponse merchants = new MerchantListResponse();

    @JsonProperty("clients")
    private ClientListResponse clients = new ClientListResponse();

    @JsonProperty("date")
    private LocalDateTime date = null;

    public Long getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(Long idMeeting) {
        this.idMeeting = idMeeting;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public MerchantListResponse getMerchants() {
        return merchants;
    }

    public void setMerchants(MerchantListResponse merchants) {
        this.merchants = merchants;
    }

    public void addMerchantResponse(MerchantResponse merchantResponse){
        this.merchants.addMerchantResponse(merchantResponse);
    }

    public ClientListResponse getClients() {
        return clients;
    }

    public void setClients(ClientListResponse clients) {
        this.clients = clients;
    }

    public void addClientResponse(ClientResponse clientResponse){
        this.clients.addClientResponse(clientResponse);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
