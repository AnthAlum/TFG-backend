package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

@Validated
public class MerchantResponse {
    @JsonProperty("idMerchant")
    private Long idMerchant = null;

    @JsonProperty("idRole")
    private Integer idRole = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("phone")
    private String phone = null;

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

    @Schema(example = "0", description = "")

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

}
