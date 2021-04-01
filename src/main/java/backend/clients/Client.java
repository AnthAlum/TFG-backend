package backend.clients;

import backend.meetings.Meeting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Long remind = Long.parseLong("5");

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "related_client",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_meeting")
    )
    private List<Meeting> meetings = new ArrayList<>();
    /*   CTOR., GETTERS, SETTERS    */
    public Client(){}

    public Client(Long idClient, String name, String email, String phone, String company, Long remind, List<Meeting> meetings) {
        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.remind = remind;
        this.meetings = meetings;
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

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public void addMeeting(Meeting meeting){
        this.meetings.add(meeting);
    }

    public void deleteMeeting(Meeting meeting){
        this.meetings.remove(meeting);
    }

    public Long getRemind() {
        return remind;
    }

    public void setRemind(Long remind) {
        this.remind = remind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(idClient, client.idClient) && Objects.equals(name, client.name) && Objects.equals(email, client.email) && Objects.equals(phone, client.phone) && Objects.equals(company, client.company) && Objects.equals(remind, client.remind) && Objects.equals(meetings, client.meetings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, name, email, phone, company, remind, meetings);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                ", remind=" + remind +
                '}';
    }
}
