package backend.merchants;

import backend.api.merchants.*;
import backend.utility.AlreadyRegisteredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface MerchantsService extends UserDetailsService {
    public Merchant findMerchantById(Long idMerchant);
    public MerchantResponse getMerchantById(Long idMerchant);
    public void registerMerchant(MerchantRegistrationRequest merchantRegistrationRequest) throws AlreadyRegisteredException;
    public MerchantPaginatedResponse getMerchants(Integer pageNumber, Integer size);
    public void deleteMerchant(Long idMerchant);
    public void modifyMerchantName(MerchantNameChangeRequest merchantNameChangeRequest, Long idMerchant);
    public void modifyMerchantEmail(MerchantEmailChangeRequest merchantEmailChangeRequest, Long idMerchant)throws AlreadyRegisteredException;
    public void modifyMerchantPhone(MerchantPhoneChangeRequest merchantPhoneChangeRequest, Long idMerchant);
    public void modifyMerchantRole(MerchantRoleChangeRequest merchantRoleChangeRequest, Long idMerchant);
    public void modifyMerchantPassword(MerchantPasswordChangeRequest merchantPasswordChangeRequest, Long idMerchant);
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
