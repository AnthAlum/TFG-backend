package backend.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class ClientCompanyChangeRequest {
    @JsonProperty("nuevaEmpresa")
    private String newCompany = null;

    public String getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(String newCompany) {
        this.newCompany = newCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientCompanyChangeRequest that = (ClientCompanyChangeRequest) o;
        return Objects.equals(newCompany, that.newCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newCompany);
    }

    @Override
    public String toString() {
        return "ClientCompanyChangeRequest{" +
                "newCompany='" + newCompany + '\'' +
                '}';
    }
}
