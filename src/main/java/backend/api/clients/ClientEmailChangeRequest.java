package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientEmailChangeRequest {
    @JsonProperty("newEmail")
    private String newEmail = null;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEmailChangeRequest that = (ClientEmailChangeRequest) o;
        return Objects.equals(newEmail, that.newEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newEmail);
    }

    @Override
    public String toString() {
        return "ClientEmailChangeRequest{" +
                "newEmail='" + newEmail + '\'' +
                '}';
    }
}
