package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class ClientResponse {
    @JsonProperty("idClient")
    private Long idClient = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("phone")
    private String phone = null;

    @JsonProperty("company")
    private String company = null;

    @JsonProperty("remind")
    private Long remind = null;

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

    public Long getRemind() {
        return remind;
    }

    public void setRemind(Long remind) {
        this.remind = remind;
    }
}
