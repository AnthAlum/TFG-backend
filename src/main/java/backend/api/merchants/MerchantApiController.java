package backend.api;

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
public class MerchantApiController implements io.swagger.api.MerchantApi {

    private static final Logger log = LoggerFactory.getLogger(MerchantApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public MerchantApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> deleteMerchant(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Comercial> getMerchant(@Parameter(in = ParameterIn.PATH, description = "Merchant ID", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Comercial>(objectMapper.readValue("{\r\n  \"cif\" : \"12345678H\",\r\n  \"idRol\" : 1,\r\n  \"id\" : 12345,\r\n  \"telefono\" : \"123456789\",\r\n  \"nombre\" : \"Juan Antonio Gonzalez Carrasco1\",\r\n  \"email\" : \"juangoncarrasco@example.com\"\r\n}", Comercial.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Comercial>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Comercial>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> modifyMerchant(@Parameter(in = ParameterIn.PATH, description = "El ID del comercial a modificar.", required=true, schema=@Schema()) @PathVariable("merchantId") Long merchantId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Comercial body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}