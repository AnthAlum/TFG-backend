package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientRegistrationRequest {
    @JsonProperty(value = "name")
    private String name = null;

    @JsonProperty(value = "phone")
    private String phone = null;

    @JsonProperty(value = "email")
    private String email = null;

    @JsonProperty(value = "company")
    private String company = null;

    @JsonProperty(value = "remind")
    private Long remind = null;

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

    public Long getRemind() {
        return remind;
    }

    public void setRemind(Long remind) {
        this.remind = remind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRegistrationRequest that = (ClientRegistrationRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(company, that.company) && Objects.equals(remind, that.remind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, company, remind);
    }

    @Override
    public String toString() {
        return "ClientRegistrationRequest{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", remind=" + remind +
                '}';
    }
}
