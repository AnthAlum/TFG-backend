package backend.authentication;

import backend.api.security.LoginRequest;
import backend.api.security.LoginResponse;

public interface AuthenticationService {
    public LoginResponse loginUser(LoginRequest loginRequest);
}
