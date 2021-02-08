package backend.api.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class LoginResponse {

    @JsonProperty("JWT")
    private String jwt = null;

    public String getJwt() {
        return jwt;
    }

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse that = (LoginResponse) o;
        return Objects.equals(jwt, that.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwt);
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
