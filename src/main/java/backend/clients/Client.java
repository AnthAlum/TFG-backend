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

    /*   CTOR., GETTERS, SETTERS    */
    public Client(){}

    public Client(Long idClient, String name, String email, String phone, String company) {
        this.idClient = idClient;
        this.name = name;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
