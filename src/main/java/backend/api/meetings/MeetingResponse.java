package backend.api.meetings;

import backend.api.clients.ClientListResponse;
import backend.api.clients.ClientResponse;
import backend.api.merchants.MerchantListResponse;
import backend.api.merchants.MerchantResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

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
    private String date = null;

    @JsonProperty("time")
    private String time = null;

    @JsonProperty("keywords")
    private List<String> keywords = new ArrayList<>();

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
