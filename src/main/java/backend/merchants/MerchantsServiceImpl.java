package backend.merchants;

import backend.api.merchants.*;
import backend.api.others.PaginationInfo;
import backend.utility.AlreadyRegisteredException;
import backend.utility.BadPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantsServiceImpl implements MerchantsService {
    private MerchantRepository merchantRepository;
    private MerchantMapper merchantMapper;

    public MerchantsServiceImpl() {
    }

    @Autowired
    public MerchantsServiceImpl(MerchantRepository merchantRepository, MerchantMapper merchantMapper) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
    }

    @Override
    public Merchant findMerchantById(Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        return merchant;
    }

    @Override
    public MerchantResponse getMerchantById(Long idMerchant) {
        Merchant merchant = findMerchantById(idMerchant);
        MerchantResponse merchantResponse = merchantMapper.merchantToMerchantResponse(merchant);
        return merchantResponse;
    }

    @Override
    public void registerMerchant(MerchantRegistrationRequest merchantRegistrationRequest) throws AlreadyRegisteredException {
        Merchant merchant = merchantRepository.findMerchantByEmail(merchantRegistrationRequest.getEmail());
        if(merchant != null)
            throw new AlreadyRegisteredException("Already registered");
        Merchant newMerchant = merchantMapper.merchantRegistrationRequestToMerchant(merchantRegistrationRequest);
        merchantRepository.save(newMerchant);
    }

    @Override
    public MerchantPaginatedResponse getMerchants(Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.searchMerchants(PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int)merchantRepository.count());
    }

    @Override
    public MerchantPaginatedResponse getMerchantsByIdRole(Integer idRole, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByIdRole(idRole, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByIdRole(idRole));
    }

    @Override
    public MerchantPaginatedResponse getMerchantsByEmail(String email, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByEmailContains(email, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByEmailContains(email));
    }

    @Override
    public MerchantPaginatedResponse getMerchantsByName(String name, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByNameContains(name, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByNameContains(name));
    }

    @Override
    public MerchantPaginatedResponse getMerchantsByPhone(String phone, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByPhoneContains(phone, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByPhoneContains(phone));
    }

    @Override
    public MerchantPaginatedResponse buildResponse(Page<Merchant> merchantPage, int totalElements){
        if(merchantPage == null) {
            return null;
        }
        MerchantListResponse merchantListResponse = new MerchantListResponse(); //Crear el response de la lista vacia de comerciales
        merchantPage.forEach(merchant ->
                merchantListResponse.addMerchantResponse(
                        merchantMapper.merchantToMerchantResponse(merchant)
                )); //Ir agregando
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setTotalElements(totalElements);
        paginationInfo.setTotalPages(merchantPage.getTotalPages());

        MerchantPaginatedResponse merchantPaginatedResponse = new MerchantPaginatedResponse();
        merchantPaginatedResponse.setPages(merchantListResponse.getMerchantResponseList());
        merchantPaginatedResponse.setPaginationInfo(paginationInfo);
        return merchantPaginatedResponse;
    }

    @Override
    public void deleteMerchant(Long idMerchant){
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchantRepository.delete(merchant);
        }
    }

    @Override
    public void modifyMerchantName(MerchantNameChangeRequest merchantNameChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setName(merchantNameChangeRequest.getNewName());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void modifyMerchantEmail(MerchantEmailChangeRequest merchantEmailChangeRequest, Long idMerchant) throws AlreadyRegisteredException{
        Merchant merchant = merchantRepository.findMerchantByEmail(merchantEmailChangeRequest.getNewEmaiL());
        if(merchant != null)
            throw new AlreadyRegisteredException("Already registered");
        merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setEmail(merchantEmailChangeRequest.getNewEmaiL());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void modifyMerchantPhone(MerchantPhoneChangeRequest merchantPhoneChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setPhone(merchantPhoneChangeRequest.getNewPhone());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void modifyMerchantRole(MerchantRoleChangeRequest merchantRoleChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setIdRole(merchantRoleChangeRequest.getNewRole());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void modifyMerchantPassword(MerchantPasswordChangeRequest merchantPasswordChangeRequest, Long idMerchant) throws BadPasswordException {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            if(merchant.getPassword().equals(merchantPasswordChangeRequest.getPassword())){
                merchant.setPassword(merchantPasswordChangeRequest.getNewPassword());
                merchantRepository.save(merchant);
            } else{
                throw new BadPasswordException("Wrong password");
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<MerchantResponse> pages = getMerchants(0, 5).getPages();
        Merchant merchant = merchantRepository.findMerchantByEmail(email);
        return merchant;
    }
}
