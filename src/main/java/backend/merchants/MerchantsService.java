package backend.merchants;

import backend.api.merchants.MerchantRegistrationRequest;
import backend.api.merchants.MerchantResponse;
import org.springframework.stereotype.Service;

@Service
public interface MerchantsService {
    public Merchant findMerchantById(Long idMerchant);
    public MerchantResponse getMerchantById(Long idMerchant);
    public void registerMerchant(MerchantRegistrationRequest merchantRegistrationRequest);
}
