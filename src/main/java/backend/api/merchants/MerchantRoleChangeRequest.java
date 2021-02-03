package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class MerchantRoleChangeRequest {
    @JsonProperty("newRole")
    private Integer newRole = null;

    public Integer getNewRole() {
        return newRole;
    }

    public void setNewRole(Integer newRole) {
        this.newRole = newRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantRoleChangeRequest that = (MerchantRoleChangeRequest) o;
        return Objects.equals(newRole, that.newRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newRole);
    }

    @Override
    public String toString() {
        return "MerchantRoleChangeRequest{" +
                "newRole=" + newRole +
                '}';
    }
}
