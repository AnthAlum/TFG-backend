package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class MerchantNameChangeRequest {
    @JsonProperty("nuevoNombre")
    private String newName = null;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantNameChangeRequest that = (MerchantNameChangeRequest) o;
        return Objects.equals(newName, that.newName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newName);
    }

    @Override
    public String toString() {
        return "MerchantNameChangeRequest{" +
                "newName='" + newName + '\'' +
                '}';
    }
}
