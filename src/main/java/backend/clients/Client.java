package backend.clients;

import backend.merchants.Merchant;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    /*   ATTRIBUTES  */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_client")
    private Integer idClient;

    private String nombre;
    private String email;
    private String telefono;
    private String empresa;

    /*   RELATION   */
    @ManyToOne
    private Merchant merchant;

    /*   CTOR., GETTERS, SETTERS    */
    public Client(Integer idClient, Merchant merchant, String nombre, String email, String telefono, String empresa) {
        this.idClient = idClient;
        this.merchant = merchant;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.empresa = empresa;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
