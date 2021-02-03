package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientRegistrationRequest {
    @JsonProperty("idCliente")
    private Long idClient = null;

    @JsonProperty("nombre")
    private String name = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("telefono")
    private String phone = null;

    @JsonProperty("empresa")
    private String company = null;

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

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
        return Objects.equals(idClient, that.idClient) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, name, email, phone, company);
    }

    @Override
    public String toString() {
        return "ClientRegistrationRequest{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
