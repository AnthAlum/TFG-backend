package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class MeetingMatterChangeRequest {
    @JsonProperty("newMatter")
    private String matter;

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }
}
