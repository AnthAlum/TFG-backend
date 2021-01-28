package backend.merchants;

import org.springframework.stereotype.Service;

@Service
public interface MerchantsService {
    public Merchant findMerchantById(Long idMerchant);
    public UserResponse getUserById(Long idUser);
    public void registerMerchant(MerchantRegistrationRequest registrationRequest);
}
