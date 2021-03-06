package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientPhoneChangeRequest {
    @JsonProperty("newPhone")
    private String newPhone = null;

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientPhoneChangeRequest that = (ClientPhoneChangeRequest) o;
        return Objects.equals(newPhone, that.newPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newPhone);
    }

    @Override
    public String toString() {
        return "ClientPhoneChangeRequest{" +
                "newPhone='" + newPhone + '\'' +
                '}';
    }
}
