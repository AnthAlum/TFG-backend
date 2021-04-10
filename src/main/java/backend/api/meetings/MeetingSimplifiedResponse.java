package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class MeetingSimplifiedResponse {
    @JsonProperty("idMeeting")
    private Long idMeeting = null;

    @JsonProperty("matter")
    private String matter = null;

    @JsonProperty("date")
    private String date = null;

    @JsonProperty("merchants")
    private Long merchants;

    @JsonProperty("clients")
    private Long clients;

    public MeetingSimplifiedResponse() {
    }

    public MeetingSimplifiedResponse(Long idMeeting, String matter, String date, Long merchants, Long clients) {
        this.idMeeting = idMeeting;
        this.matter = matter;
        this.date = date;
        this.merchants = merchants;
        this.clients = clients;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getMerchants() {
        return merchants;
    }

    public void setMerchants(Long merchants) {
        this.merchants = merchants;
    }

    public Long getClients() {
        return clients;
    }

    public void setClients(Long clients) {
        this.clients = clients;
    }
}
