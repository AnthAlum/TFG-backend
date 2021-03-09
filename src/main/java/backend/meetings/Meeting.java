package backend.meetings;

import backend.clients.Client;
import backend.merchants.Merchant;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_meeting")
    private Long idMeeting;

    private String matter;

    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "merchant_id", referencedColumnName = "id_merchant")
    private Merchant merchant;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id", referencedColumnName = "id_client")
    private Client client;

    public Meeting() {
    }

    public Meeting(Long idMeeting, String matter, LocalDateTime date, Merchant merchant, Client client) {
        this.idMeeting = idMeeting;
        this.matter = matter;
        this.date = date;
        this.merchant = merchant;
        this.client = client;
    }

    public Long getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(Long idMeeting) {
        this.idMeeting = idMeeting;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
