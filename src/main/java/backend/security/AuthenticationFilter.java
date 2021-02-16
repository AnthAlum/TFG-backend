package backend.security;

import backend.merchants.MerchantsService;
import backend.security.jwt.JwtConfig;
import backend.security.jwt.JwtSecretKey;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {

    private JwtConfig jwtConfig;
    private JwtSecretKey jwtSecretKey;
    private MerchantsService merchantsService;

    @Autowired
    public AuthenticationFilter(JwtConfig jwtConfig, JwtSecretKey jwtSecretKey, MerchantsService merchantsService) {
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
        this.merchantsService = merchantsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtConfig.getAuthorizationHeader());
        if(!Strings.isNullOrEmpty(header)){
            String token = header.replace(jwtConfig.getTokenPrefix(), ""); //Quitamos el prefijo
            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(jwtSecretKey.secretKey())
                        .build()
                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String userEmail = body.getSubject();
                UserDetails userDetails = merchantsService.loadUserByUsername(userEmail);
                List<Map<String, String>> authorities = (List<Map<String, String>>)body.get("authorities");
                Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        grantedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch(JwtException e){

            }
        }
        filterChain.doFilter(request, response);
    }
}
