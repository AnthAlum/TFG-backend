package backend.clients;

import backend.meetings.Meeting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    /*   ATTRIBUTES  */
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    @Column(name = "id_client")
    private Long idClient;

    private String name;
    private String email;
    private String phone;
    private String company;

    @OneToMany
    private List<Meeting> meetings = new ArrayList<>();
    /*   CTOR., GETTERS, SETTERS    */
    public Client(){}

    public Client(Long idClient, String name, String email, String phone, String company, List<Meeting> meetings) {
        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.company = company;
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
}
