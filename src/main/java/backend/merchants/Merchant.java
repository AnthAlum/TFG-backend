package backend.merchants;


import backend.meetings.Meeting;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "merchant")
public class Merchant implements UserDetails {
    /*   ATTRIBUTES  */
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    @Column(name = "id_merchant")
    private Long idMerchant;
    /**
     * Value 0 for Admin or Value 1 for User
     */
    @Column(name = "id_role")
    private Integer idRole;

    private String email;
    private String name;
    private String phone;
    private String password;

    @OneToMany
    private List<Meeting> meetings = new ArrayList<>();

    /*   CTOR., GETTERS, SETTERS    */
    public Merchant(){}

    public Merchant(Long idMerchant, Integer idRole, String email, String name, String phone, String password, List<Meeting> meetings) {
        this.idMerchant = idMerchant;
        this.idRole = idRole;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.meetings = meetings;
    }

    public Long getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
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

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String assignedRole = null;
        if(getIdRole() == 0)
            assignedRole = "ROLE_ADMIN";
        else if(getIdRole() == 1)
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addMeeting(Meeting meeting){
        this.meetings.add(meeting);
    }
}
