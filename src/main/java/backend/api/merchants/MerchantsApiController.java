package backend.api.merchants;

import backend.merchants.MerchantsService;
import backend.utility.AlreadyRegisteredException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MerchantsApiController implements MerchantsApi {

    private static final Logger log = LoggerFactory.getLogger(MerchantsApiController.class);

    private MerchantsService merchantsService;

    @Autowired
    public MerchantsApiController( MerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    public ResponseEntity<Void> addMerchant(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody MerchantRegistrationRequest body) {
        try{
            merchantsService.registerMerchant(body);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch(AlreadyRegisteredException E){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<MerchantPaginatedResponse> getMerchants(Integer pageNumber, Integer size) {
        MerchantPaginatedResponse merchants = merchantsService.getMerchants(pageNumber, size);
        if(merchants == null)
            return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantsSimplifiedListResponse> getMerchantsSimplified() {
        MerchantsSimplifiedListResponse merchants = merchantsService.getMerchantsSimplified();
        if(merchants == null)
            return new ResponseEntity<MerchantsSimplifiedListResponse>(merchants, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MerchantsSimplifiedListResponse>(merchants, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantPaginatedResponse> getMerchantsByEmail(@Valid String email, @Valid Integer pageNumber, @Valid Integer size) {
        MerchantPaginatedResponse merchants = merchantsService.getMerchantsByEmail(email, pageNumber, size);
        if(merchants == null)
            return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantPaginatedResponse> getMerchantsByName(@Valid String name, @Valid Integer pageNumber, @Valid Integer size) {
        MerchantPaginatedResponse merchants = merchantsService.getMerchantsByName(name, pageNumber, size);
        if(merchants == null)
            return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantPaginatedResponse> getMerchantsByPhone(@Valid String phone, @Valid Integer pageNumber, @Valid Integer size) {
        MerchantPaginatedResponse merchants = merchantsService.getMerchantsByPhone(phone, pageNumber, size);
        if(merchants == null)
            return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantPaginatedResponse> getMerchantsByIdRole(@Valid Integer idRole, @Valid Integer pageNumber, @Valid Integer size) {
        MerchantPaginatedResponse merchants = merchantsService.getMerchantsByIdRole(idRole, pageNumber, size);
        if(merchants == null)
            return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.OK);
    }
}
