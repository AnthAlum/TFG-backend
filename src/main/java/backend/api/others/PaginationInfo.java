package backend.api.others;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class PaginationInfo {
    @JsonProperty("totalPages")
    private Integer totalPages = null;

    @JsonProperty("totalElements")
    private Integer totalElements = null;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaginationInfo that = (PaginationInfo) o;
        return Objects.equals(totalPages, that.totalPages) && Objects.equals(totalElements, that.totalElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPages, totalElements);
    }

    @Override
    public String toString() {
        return "PaginationInfo{" +
                "totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                '}';
    }
}
