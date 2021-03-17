package backend.api.merchants;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class MerchantsSimplifiedListResponse {
    @JsonProperty("simplifiedList")
    private List<MerchantSimplifiedResponse> merchantSimplifiedResponseList = new ArrayList<>();

    public List<MerchantSimplifiedResponse> getMerchantSimplifiedResponseList() {
        return merchantSimplifiedResponseList;
    }

    public void setMerchantSimplifiedResponseList(List<MerchantSimplifiedResponse> merchantSimplifiedResponseList) {
        this.merchantSimplifiedResponseList = merchantSimplifiedResponseList;
    }

    public void addResponse(MerchantSimplifiedResponse merchantSimplifiedResponse){
        this.merchantSimplifiedResponseList.add(merchantSimplifiedResponse);
    }
}
