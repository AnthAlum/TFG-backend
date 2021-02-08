package backend.authentication;

import backend.api.security.LoginRequest;
import backend.api.security.LoginResponse;
import backend.merchants.MerchantsService;
import backend.security.jwt.JwtConfig;
import backend.security.jwt.JwtSecretKey;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private MerchantsService merchantsService;
    private JwtConfig jwtConfig;
    private JwtSecretKey jwtSecretKey;

    @Autowired
    public AuthenticationServiceImpl(MerchantsService merchantsService, JwtConfig jwtConfig, JwtSecretKey jwtSecretKey) {
        this.merchantsService = merchantsService;
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        try{ //Hacer un login response que contenga el JWT con los datos del cliente encontrado
            String username = loginRequest.getUsername();
            Integer expirationAfterDays = jwtConfig.getTokenExpirationAfterDays();
            UserDetails user = merchantsService.loadUserByUsername(username);
//            if(user.getPassword() != loginRequest.getPassword())
//                throw new UnsuccessfulLoginException();
            String jwt = Jwts.builder()
                    .setSubject(username)
                    .claim("authorities", user.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(expirationAfterDays)))
                    .signWith(jwtSecretKey.secretKey())
                    .compact();
            return new LoginResponse(jwtConfig.getTokenPrefix() + jwt);
        }catch (UsernameNotFoundException e) {
            return new LoginResponse("USERNAME/PASSWORD INCORRECT");
        }
//        catch (UnsuccessfulLoginException e){
//            return new LoginResponse("USERNAME/PASSWORD INCORRECT");
//        }
    }
}
