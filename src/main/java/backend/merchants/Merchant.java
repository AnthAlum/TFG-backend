package backend.merchants;


import backend.meetings.Meeting;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "id_role")
    private Integer idRole;

    private String email;
    private String name;
    private String phone;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "related_merchant",
            joinColumns = @JoinColumn(name = "id_merchant"),
            inverseJoinColumns = @JoinColumn(name = "id_meeting")
    )
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

    public void deleteMeeting(Meeting meeting){
        this.meetings.remove(meeting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchant merchant = (Merchant) o;
        return Objects.equals(idMerchant, merchant.idMerchant) && Objects.equals(idRole, merchant.idRole) && Objects.equals(email, merchant.email) && Objects.equals(name, merchant.name) && Objects.equals(phone, merchant.phone) && Objects.equals(password, merchant.password) && Objects.equals(meetings, merchant.meetings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMerchant, idRole, email, name, phone, password, meetings);
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "idMerchant=" + idMerchant +
                ", idRole=" + idRole +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", meetings=" + meetings +
                '}';
    }
}
