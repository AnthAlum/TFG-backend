package backend.api.meetings;

import backend.api.others.PaginationInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class MeetingPaginatedResponse {
    @JsonProperty("pages")
    private List<MeetingResponse> pages = new ArrayList<>();

    @JsonProperty("paginationInfo")
    private PaginationInfo paginationInfo = null;

    public List<MeetingResponse> getPages() {
        return pages;
    }

    public void setPages(List<MeetingResponse> pages) {
        this.pages = pages;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        this.paginationInfo = paginationInfo;
    }
}
