package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Validated
public class MeetingRegistrationRequest {
    @JsonProperty(value = "matter")
    private String matter = null;

    @JsonProperty(value = "localDateTime")
    private LocalDateTime localDateTime = null;

    @JsonProperty(value = "merchants")
    private ArrayList<Long> merchants = null;

    @JsonProperty(value = "clients")
    private ArrayList<Long> clients = null;

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ArrayList<Long> getMerchants() {
        return merchants;
    }

    public void setMerchants(ArrayList<Long> merchants) {
        this.merchants = merchants;
    }

    public ArrayList<Long> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Long> clients) {
        this.clients = clients;
    }
}
