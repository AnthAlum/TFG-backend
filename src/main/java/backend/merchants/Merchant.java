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

    private String name;
    private String email;
    private String cif;
    private String telefono;

    /*   RELATION   */
    @OneToMany
    private Set<Client> clients;

    /*   CTOR., GETTERS, SETTERS    */
    public Merchant(Long idMerchant, Integer idRol, String name, String email, String cif, String telefono) {
        this.idMerchant = idMerchant;
        this.idRol = idRol;
        this.name = name;
        this.email = email;
        this.cif = cif;
        this.telefono = telefono;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
