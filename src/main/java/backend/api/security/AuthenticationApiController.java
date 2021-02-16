package backend.api.security;

import backend.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationApiController implements AuthenticationApi{

    AuthenticationService authenticationService;

    @Autowired
    public AuthenticationApiController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<LoginResponse> userLogin(@Valid LoginRequest body) {
        System.out.println("\nCredentials recieved:\n" + body.getUsername() + "\n" + body.getPassword());
        LoginResponse loginResponse = authenticationService.loginUser(body);
        System.out.println("\nCredentials returned:\n" + loginResponse.getJwt());
        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
    }
}
