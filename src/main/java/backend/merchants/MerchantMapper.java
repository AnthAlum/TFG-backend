package backend.merchants;

import backend.api.merchants.MerchantRegistrationRequest;
import backend.api.merchants.MerchantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    @Mapping(target = "meetings", ignore = true)
    MerchantResponse merchantToMerchantResponse(Merchant merchant);

    @Mapping(target = "idMerchant", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    @Mapping(target = "password", ignore = true)
    Merchant merchantRegistrationRequestToMerchant(MerchantRegistrationRequest merchantRegistrationRequest);

}
