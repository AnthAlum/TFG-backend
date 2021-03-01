package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class MerchantPasswordChangeRequest {
    @JsonProperty("password")
    private String password = null;

    @JsonProperty("newPassword")
    private String newPassword = null;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantPasswordChangeRequest that = (MerchantPasswordChangeRequest) o;
        return Objects.equals(password, that.password) && Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, newPassword);
    }
}
