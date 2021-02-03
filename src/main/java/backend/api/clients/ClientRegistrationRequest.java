package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientRegistrationRequest {
    @JsonProperty(value = "name", index = 0)
    private String name = null;

    @JsonProperty(value = "phone", index = 1)
    private String phone = null;

    @JsonProperty(value = "email", index = 2)
    private String email = null;

    @JsonProperty(value = "company", index = 3)
    private String company = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRegistrationRequest that = (ClientRegistrationRequest) o;
        return  Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone, company);
    }

    @Override
    public String toString() {
        return "ClientRegistrationRequest{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
