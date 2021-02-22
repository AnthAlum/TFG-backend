package backend.api.merchants;

import backend.merchants.MerchantsService;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")
@RestController
public class MerchantsApiController implements MerchantsApi {

    private static final Logger log = LoggerFactory.getLogger(MerchantsApiController.class);

    private MerchantsService merchantsService;

    @Autowired
    public MerchantsApiController( MerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    public ResponseEntity<Void> addMerchant(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody MerchantRegistrationRequest body) {
        merchantsService.registerMerchant(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MerchantPaginatedResponse> getMerchants(Integer pageNumber, Integer size) {
        MerchantPaginatedResponse merchants = merchantsService.getMerchants(pageNumber, size);
        if(merchants == null)
            return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<MerchantPaginatedResponse>(merchants, HttpStatus.OK);
    }

}
