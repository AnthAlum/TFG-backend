package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientRemindChangeRequest {
    @JsonProperty("newRemind")
    private Long newRemind = null;

    public Long getNewRemind() {
        return newRemind;
    }

    public void setNewRemind(Long newRemind) {
        this.newRemind = newRemind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRemindChangeRequest that = (ClientRemindChangeRequest) o;
        return Objects.equals(newRemind, that.newRemind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newRemind);
    }

    @Override
    public String toString() {
        return "ClientRemindChangeRequest{" +
                "newRemind=" + newRemind +
                '}';
    }
}
