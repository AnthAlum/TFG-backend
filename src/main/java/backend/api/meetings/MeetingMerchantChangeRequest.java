package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class MeetingMerchantChangeRequest {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("merchantId")
    private Long merchantId;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
