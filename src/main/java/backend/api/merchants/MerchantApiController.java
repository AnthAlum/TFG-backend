package backend.api.merchants;

import backend.api.merchants.MerchantResponse;
import backend.merchants.MerchantsService;
import io.swagger.model.Comercial;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T22:09:33.361636800+01:00[Europe/Paris]")
@RestController
public class MerchantApiController implements MerchantApi {

    private static final Logger log = LoggerFactory.getLogger(MerchantApiController.class);

    private MerchantsService merchantsService;

    @org.springframework.beans.factory.annotation.Autowired
    public MerchantApiController(MerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    public ResponseEntity<Void> deleteMerchant(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId) {
        merchantsService.deleteMerchant(merchantId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<MerchantResponse> getMerchant(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId) {
        return new ResponseEntity<MerchantResponse>(merchantsService.getMerchantById(merchantId), HttpStatus.OK);
    }

    public ResponseEntity<Void> modifyMerchant(@Parameter(in = ParameterIn.PATH, description = "El ID del comercial a modificar.", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Comercial body) {
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
