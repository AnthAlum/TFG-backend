package backend.merchants;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "merchant")
public class Merchant implements UserDetails {
    /*   ATTRIBUTES  */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_merchant")
    private Long idMerchant;
    /**
     * Value 0 for Admin or Value 1 for User
     */
    @Column(name = "id_rol")
    private Integer idRol;

    private String email;
    private String name;
    private String phone;
    private String password;


    /*   CTOR., GETTERS, SETTERS    */
    public Merchant(){}

    public Merchant(Long idMerchant, Integer idRol, String name, String email, String phone, String password) {
        this.idMerchant = idMerchant;
        this.idRol = idRol;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Long getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // TODO: HAY QUE CREAR UNA ENTIDAD PARA LOS ROLES
        String assignedRole = null;
        if(getIdRol() == 0)
            assignedRole = "ROLE_ADMIN";
        else
            assignedRole = "ROLE_USER";
        authorities.add(new SimpleGrantedAuthority(assignedRole));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
