package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
public class MerchantListResponse {
    @JsonProperty("Merchants")
    private List<MerchantResponse> merchantResponseList;

    public MerchantListResponse() {
        this.merchantResponseList = new ArrayList<>();
    }

    public MerchantListResponse(List<MerchantResponse> merchantResponseList) {
        this.merchantResponseList = merchantResponseList;
    }

    public List<MerchantResponse> getMerchantResponseList(){
        return merchantResponseList;
    }

    public void setMerchantResponseList(List<MerchantResponse> merchantResponseList){
        this.merchantResponseList = merchantResponseList;
    }

    public void addMerchantResponse(MerchantResponse merchantResponse){
        merchantResponseList.add(merchantResponse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantListResponse that = (MerchantListResponse) o;
        return Objects.equals(merchantResponseList, that.merchantResponseList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantResponseList);
    }

    @Override
    public String toString() {
        return "MerchantListResponse{" +
                "merchantResponseList=" + merchantResponseList +
                '}';
    }

}
