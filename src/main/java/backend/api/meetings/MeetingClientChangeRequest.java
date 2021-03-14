package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class MeetingClientChangeRequest {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("clientId")
    private Long clientId;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
