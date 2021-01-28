package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class MerchantResponse {
    @JsonProperty("idMerchant")
    private Long idMerchant = null;

    @JsonProperty("nombre")
    private String name = null;

    @JsonProperty("idRol")
    private Integer idRol = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("telefono")
    private String phone = null;

    @JsonProperty("empresa")
    private String company = null;

    /**
     * Get id
     * @return id
     **/
    @Schema(example = "67890", description = "")
    public Long getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
    }

    /**
     * Get nombre
     * @return nombre
     **/
    @Schema(example = "Fernando Carlos Roca Rivas", description = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get email
     * @return email
     **/
    @Schema(example = "correoexample@example.com", description = "")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get telefono
     * @return telefono
     **/
    @Schema(example = "987654321", description = "")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get empresa
     * @return empresa
     **/
    @Schema(example = "Cinco Jotas", description = "")

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Schema(example = "0", description = "")

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantResponse that = (MerchantResponse) o;
        return Objects.equals(idMerchant, that.idMerchant) && Objects.equals(name, that.name) && Objects.equals(idRol, that.idRol) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMerchant, name, idRol, email, phone, company);
    }

    @Override
    public String toString() {
        return "MerchantResponse{" +
                "idMerchant=" + idMerchant +
                ", name='" + name + '\'' +
                ", idRol=" + idRol +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


}
