package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class MerchantRegistrationRequest {
    @JsonProperty("idMerchant")
    private Long idMerchant = null;

    @JsonProperty("nombre")
    private String name = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("telefono")
    private String phone = null;

    @JsonProperty("empresa")
    private String company = null;

    public Long getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
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
        MerchantRegistrationRequest that = (MerchantRegistrationRequest) o;
        return Objects.equals(idMerchant, that.idMerchant) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMerchant, name, email, phone, company);
    }

    @Override
    public String toString() {
        return "MerchantRegistrationRequest{" +
                "idMerchant=" + idMerchant +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
