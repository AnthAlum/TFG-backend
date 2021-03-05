package backend.api.clients;

import backend.api.others.PaginationInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class ClientPaginatedResponse {
    @JsonProperty("pages")
    private List<ClientResponse> pages = new ArrayList<>();

    @JsonProperty("paginationInfo")
    private PaginationInfo paginationInfo = null;

    public List<ClientResponse> getPages() {
        return pages;
    }

    public void setPages(List<ClientResponse> pages) {
        this.pages = pages;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        this.paginationInfo = paginationInfo;
    }
}
