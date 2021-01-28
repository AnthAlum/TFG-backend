package backend.merchants;

import backend.api.merchants.MerchantRegistrationRequest;
import backend.api.merchants.MerchantResponse;

public class MerchantsServiceImpl implements MerchantsService{

    private MerchantRepository merchantRepository;
    private MerchantMapper merchantMapper;

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
}
