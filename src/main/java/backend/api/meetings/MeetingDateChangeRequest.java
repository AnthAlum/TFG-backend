package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
public class MeetingDateChangeRequest {
    @JsonProperty("newDate")
    private LocalDateTime localDateTime;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
