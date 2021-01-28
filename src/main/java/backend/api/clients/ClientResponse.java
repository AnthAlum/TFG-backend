package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientResponse {
    @JsonProperty("idCliente")
    private Long idClient = null;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("idComerciante")
    private Long idMerchant;

    @JsonProperty("email")
    private String email;

    @JsonProperty("telefono")
    private String phone;

    @JsonProperty("empresa")
    private String company;

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

    public Long getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
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
        ClientResponse that = (ClientResponse) o;
        return Objects.equals(idClient, that.idClient) && Objects.equals(name, that.name) && Objects.equals(idMerchant, that.idMerchant) && Objects.equals(email, that.email) && phone.equals(that.phone) && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, name, idMerchant, email, phone, company);
    }
}
