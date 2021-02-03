package backend.merchants;

import backend.api.merchants.*;
import org.springframework.stereotype.Service;

@Service
public interface MerchantsService {
    public Merchant findMerchantById(Long idMerchant);
    public MerchantResponse getMerchantById(Long idMerchant);
    public void registerMerchant(MerchantRegistrationRequest merchantRegistrationRequest);
    public MerchantListResponse getMerchants();
    public void deleteMerchant(Long idMerchant);
    public void modifyMerchantName(MerchantNameChangeRequest merchantNameChangeRequest, Long idMerchant);
    public void modifyMerchantEmail(MerchantEmailChangeRequest merchantEmailChangeRequest, Long idMerchant);
    public void modifyMerchantPhone(MerchantPhoneChangeRequest merchantPhoneChangeRequest, Long idMerchant);
}
