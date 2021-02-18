package backend.merchants;

import backend.api.merchants.*;
import backend.api.others.PaginationInfo;
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
    public void registerMerchant(MerchantRegistrationRequest merchantRegistrationRequest) {
        Merchant merchant = merchantMapper.merchantRegistrationRequestToMerchant(merchantRegistrationRequest);
        merchantRepository.save(merchant);
    }

    @Override
    public MerchantPaginatedResponse getMerchants(Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.searchMerchants(PageRequest.of(pageNumber, size));
        List<Merchant> merchantList = (List<Merchant>)merchantRepository.findAll(); //Encontrar todos los comerciales
        MerchantListResponse merchantListResponse = new MerchantListResponse(); //Crear el response de la lista vacia de comerciales
        merchantPage.forEach(merchant ->
                merchantListResponse.addMerchantResponse(
                        merchantMapper.merchantToMerchantResponse(merchant)
                )); //Ir agregando
                     // MerchantsResponses a la lista tras el mapeao de cada comerciante

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setTotalElements(merchantPage.getNumberOfElements());
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
    public void modifyMerchantEmail(MerchantEmailChangeRequest merchantEmailChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setName(merchantEmailChangeRequest.getNewEmaiL());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void modifyMerchantPhone(MerchantPhoneChangeRequest merchantPhoneChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setName(merchantPhoneChangeRequest.getNewPhone());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void modifyMerchantRole(MerchantRoleChangeRequest merchantRoleChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setIdRol(merchantRoleChangeRequest.getNewRole());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void modifyMerchantPassword(MerchantPasswordChangeRequest merchantPasswordChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setPassword(merchantPasswordChangeRequest.getNewPassword());
            merchantRepository.save(merchant);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Merchant merchant = merchantRepository.findMerchantByEmail(email);
        return merchant;
    }
}
