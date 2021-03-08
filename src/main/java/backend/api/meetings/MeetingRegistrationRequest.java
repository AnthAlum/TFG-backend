package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
public class MeetingRegistrationRequest {
    @JsonProperty(value = "matter")
    private String matter = null;

    @JsonProperty(value = "localDateTime")
    private LocalDateTime localDateTime = null;

    @JsonProperty(value = "idMerchant")
    private Long idMerchant = null;

    @JsonProperty(value = "idClient")
    private Long idClient = null;

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

    public Long getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
}
