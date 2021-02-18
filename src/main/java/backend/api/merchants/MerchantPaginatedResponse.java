package backend.api.merchants;

import backend.api.others.PaginationInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class MerchantPaginatedResponse {
    @JsonProperty("pages")
    private List<MerchantResponse> pages = new ArrayList<>();

    @JsonProperty("paginationInfo")
    private PaginationInfo paginationInfo = null;

    public List<MerchantResponse> getPages() {
        return pages;
    }

    public void setPages(List<MerchantResponse> pages) {
        this.pages = pages;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        this.paginationInfo = paginationInfo;
    }
}
