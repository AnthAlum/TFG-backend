package backend.merchants;

import backend.api.merchants.*;
import backend.utility.AlreadyRegisteredException;
import backend.utility.BadPasswordException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface MerchantsService extends UserDetailsService {
    public MerchantResponse getMerchantById(Long idMerchant);
    public MerchantResponse getMerchantByEmail(String email);
    public void registerMerchant(MerchantRegistrationRequest merchantRegistrationRequest) throws AlreadyRegisteredException;
    public MerchantPaginatedResponse getMerchants(Integer pageNumber, Integer size);
    public MerchantsSimplifiedListResponse getMerchantsSimplified();
    public MerchantPaginatedResponse getMerchantsByIdRole(Integer idRole, Integer pageNumber, Integer size);
    public MerchantPaginatedResponse getMerchantsByEmail(String email, Integer pageNumber, Integer size);
    public MerchantPaginatedResponse getMerchantsByName(String name, Integer pageNumber, Integer size);
    public MerchantPaginatedResponse getMerchantsByPhone(String phone, Integer pageNumber, Integer size);
    public MerchantPaginatedResponse buildResponse(Page<Merchant> merchantPage, int totalElements);
    public void deleteMerchant(Long idMerchant);
    public void modifyMerchantName(MerchantNameChangeRequest merchantNameChangeRequest, Long idMerchant);
    public void modifyMerchantEmail(MerchantEmailChangeRequest merchantEmailChangeRequest, Long idMerchant) throws AlreadyRegisteredException;
    public void modifyMerchantPhone(MerchantPhoneChangeRequest merchantPhoneChangeRequest, Long idMerchant);
    public void modifyMerchantRole(MerchantRoleChangeRequest merchantRoleChangeRequest, Long idMerchant);
    public void modifyMerchantPassword(MerchantPasswordChangeRequest merchantPasswordChangeRequest, Long idMerchant) throws BadPasswordException;
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
