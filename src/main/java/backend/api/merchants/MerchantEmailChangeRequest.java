package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class MerchantEmailChangeRequest {
    @JsonProperty("newEmail")
    private String newEmaiL = null;

    public String getNewEmaiL() {
        return newEmaiL;
    }

    public void setNewEmaiL(String newEmaiL) {
        this.newEmaiL = newEmaiL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantEmailChangeRequest that = (MerchantEmailChangeRequest) o;
        return Objects.equals(newEmaiL, that.newEmaiL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newEmaiL);
    }

    @Override
    public String toString() {
        return "MerchantEmailChangeRequest{" +
                "newEmaiL='" + newEmaiL + '\'' +
                '}';
    }

}
