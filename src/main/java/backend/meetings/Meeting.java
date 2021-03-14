package backend.meetings;

import backend.clients.Client;
import backend.merchants.Merchant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_meeting")
    private Long idMeeting;

    private String matter;

    private LocalDateTime date;

    @ManyToMany(mappedBy = "meetings")
    private List<Merchant> merchants = new ArrayList<Merchant>();

    @ManyToMany(mappedBy = "meetings")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(idMeeting, meeting.idMeeting) && Objects.equals(matter, meeting.matter) && Objects.equals(date, meeting.date) && Objects.equals(merchants, meeting.merchants) && Objects.equals(clients, meeting.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeeting, matter, date, merchants, clients);
    }

}
