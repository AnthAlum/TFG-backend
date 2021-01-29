package backend.merchants;

import backend.api.merchants.MerchantListResponse;
import backend.api.merchants.MerchantRegistrationRequest;
import backend.api.merchants.MerchantResponse;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public MerchantListResponse getMerchants() {
        List<Merchant> merchantList = (List<Merchant>)merchantRepository.findAll(); //Encontrar todos los comerciales
        MerchantListResponse merchantListResponse = new MerchantListResponse(); //Crear el response de la lista vacia de comerciales
        merchantList.forEach(merchant ->
                merchantListResponse.addMerchantResponse(merchantMapper.merchantToMerchantResponse(merchant))); //Ir agregando
                     // MerchantsResponses a la lista tras el mapeao de cada comerciante
        return merchantListResponse;
    }
}
