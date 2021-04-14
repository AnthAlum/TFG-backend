package backend.api.security;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public interface AuthenticationApi {
    @ApiOperation(value = "Authenticates a user into the system", nickname = "userLogin", notes = "Logs in a user into the system", response = LoginResponse.class, tags={ "Authentication", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Logged in successfully", response = LoginResponse.class),
            @ApiResponse(code = 400, message = "Invalid input, object invalid"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 409, message = "The request could not be completed because of a conflict") })
    @RequestMapping(value = "/login",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<LoginResponse> userLogin(@ApiParam(value = "The user's username and password"  )  @Valid @RequestBody LoginRequest body);
}
