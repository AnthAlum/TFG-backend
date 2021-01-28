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
    private Long idClient;

    private String name;
    private String email;
    private String phone;
    private String company;

    /*   RELATION   */
    @ManyToOne
    private Merchant merchant;

    /*   CTOR., GETTERS, SETTERS    */
    public Client(Long idClient, Merchant merchant, String nombre, String email, String phone, String company) {
        this.idClient = idClient;
        this.merchant = merchant;
        this.name = nombre;
        this.email = email;
        this.phone = phone;
        this.company = company;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
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

    public void setPhone(String Phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
