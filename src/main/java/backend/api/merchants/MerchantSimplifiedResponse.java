package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class MerchantSimplifiedResponse {
    @JsonProperty("id")
    private Long idMerchant;

    @JsonProperty("nameAndLastname")
    private String nameAndLastName;

    @JsonProperty("email")
    private String email;

    public MerchantSimplifiedResponse(Long idMerchant, String nameAndLastName, String email) {
        this.idMerchant = idMerchant;
        this.nameAndLastName = nameAndLastName;
        this.email = email;
    }

    public Long getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
    }

    public String getNameAndLastName() {
        return nameAndLastName;
    }

    public void setNameAndLastName(String nameAndLastName) {
        this.nameAndLastName = nameAndLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
