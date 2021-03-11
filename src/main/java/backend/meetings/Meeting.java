package backend.meetings;

import backend.clients.Client;
import backend.merchants.Merchant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    @Column(name = "id_meeting")
    private Long idMeeting;

    private String matter;

    private LocalDateTime date;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Merchant> merchants = new ArrayList<Merchant>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Client> clients = new ArrayList<Client>();

    public Meeting() {
    }

    public Meeting(Long idMeeting, String matter, LocalDateTime date, ArrayList<Merchant> merchants, ArrayList<Client> clients) {
        this.idMeeting = idMeeting;
        this.matter = matter;
        this.date = date;
        this.merchants = merchants;
        this.clients = clients;
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

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addMerchant(Merchant merchant){
        this.merchants.add(merchant);
    }

    public void addClient(Client client){
        this.clients.add(client);
    }
}
