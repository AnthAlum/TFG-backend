package backend.merchants;

import backend.api.merchants.MerchantRegistrationRequest;
import backend.api.merchants.MerchantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    
    MerchantResponse merchantToMerchantResponse(Merchant merchant);

    Merchant merchantRegistrationRequestToMerchant(MerchantRegistrationRequest merchantRegistrationRequest);
}