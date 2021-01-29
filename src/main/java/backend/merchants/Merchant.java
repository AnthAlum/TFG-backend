package backend.merchants;


import backend.clients.Client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "merchant")
public class Merchant {
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
    private String cif;
    private String phone;
    private String company;

    /*   RELATION   */
    @OneToMany
    private Set<Client> clients;


    /*   CTOR., GETTERS, SETTERS    */
    public Merchant(){}

    public Merchant(Long idMerchant, Integer idRol, String name, String email, String cif, String phone, String company) {
        this.idMerchant = idMerchant;
        this.idRol = idRol;
        this.name = name;
        this.email = email;
        this.cif = cif;
        this.phone = phone;
        this.company = company;
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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}
