package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Validated
public class MeetingResponse {
    @JsonProperty("idMeeting")
    private Long idMeeting = null;

    @JsonProperty("matter")
    private String matter = null;

    @JsonProperty("idsMerchant")
    private ArrayList<Long> idsMerchant = null;

    @JsonProperty("idsClient")
    private ArrayList<Long> idsClient = null;

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

    public ArrayList<Long> getIdsMerchant() {
        return idsMerchant;
    }

    public void setIdsMerchant(ArrayList<Long> idsMerchant) {
        this.idsMerchant = idsMerchant;
    }

    public ArrayList<Long> getIdsClient() {
        return idsClient;
    }

    public void setIdsClient(ArrayList<Long> idsClient) {
        this.idsClient = idsClient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
