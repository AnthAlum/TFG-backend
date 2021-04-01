package backend.api.merchants;

import backend.merchants.MerchantsService;
import backend.utility.AlreadyRegisteredException;
import backend.utility.BadPasswordException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")
@RestController
public class MerchantApiController implements MerchantApi {

    private static final Logger log = LoggerFactory.getLogger(MerchantApiController.class);

    private MerchantsService merchantsService;

    @org.springframework.beans.factory.annotation.Autowired
    public MerchantApiController(MerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    @Override
    public ResponseEntity<Void> deleteMerchant(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId) {
        merchantsService.deleteMerchant(merchantId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantResponse> getMerchant(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId) {
        return new ResponseEntity<MerchantResponse>(merchantsService.getMerchantById(merchantId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantResponse> getMerchantByEmail(@Valid String merchantEmail) {
        return new ResponseEntity<MerchantResponse>(merchantsService.getMerchantByEmail(merchantEmail), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyMerchantName(Long merchantId, @Valid MerchantNameChangeRequest body) {
        merchantsService.modifyMerchantName(body, merchantId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyMerchantEmail(Long merchantId, @Valid MerchantEmailChangeRequest body) {
        try {
            merchantsService.modifyMerchantEmail(body, merchantId);
        } catch (AlreadyRegisteredException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyMerchantPhone(Long merchantId, @Valid MerchantPhoneChangeRequest body) {
        merchantsService.modifyMerchantPhone(body, merchantId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyMerchantRole(Long merchantId, @Valid MerchantRoleChangeRequest body) {
        merchantsService.modifyMerchantRole(body, merchantId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> modifyMerchantPassword(Long merchantId, @Valid MerchantPasswordChangeRequest body) {
        try{
            merchantsService.modifyMerchantPassword(body, merchantId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (BadPasswordException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}


