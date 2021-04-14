package backend.security;

import backend.merchants.MerchantsService;
import backend.security.jwt.JwtConfig;
import backend.security.jwt.JwtSecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter { //Indico que voy a usar mi configuracion
        // para la seguridad
    private PasswordEncoder passwordEncoder;
    private MerchantsService merchantsService;
    private JwtSecretKey jwtSecretKey;
    private JwtConfig jwtConfig;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          MerchantsService merchantsService,
                          JwtSecretKey jwtSecretKey,
                          JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.merchantsService = merchantsService;
        this.jwtSecretKey = jwtSecretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(Arrays.asList("*"));
                    cors.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(Arrays.asList("*"));
                    return cors;
                }).and()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/*", "/js/*").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated();

        http
                .addFilterBefore(new AuthenticationFilter(jwtConfig, jwtSecretKey), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
                "/swagger-ui.html", "/webjars/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(merchantsService).passwordEncoder(passwordEncoder);
    }


}
