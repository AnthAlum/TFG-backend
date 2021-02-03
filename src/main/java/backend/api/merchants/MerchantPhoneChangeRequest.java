package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class MerchantPhoneChangeRequest {
    @JsonProperty("nuevoTelefono")
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
        MerchantPhoneChangeRequest that = (MerchantPhoneChangeRequest) o;
        return Objects.equals(newPhone, that.newPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newPhone);
    }

    @Override
    public String toString() {
        return "MerchantPhoneChangeRequest{" +
                "newPhone='" + newPhone + '\'' +
                '}';
    }
}
