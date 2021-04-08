package backend.meetings;

import backend.clients.Client;
import backend.filemanagment.File;
import backend.merchants.Merchant;
import backend.notification.Notification;

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

    @Lob
    @Column(columnDefinition = "Text")
    private String description;

    private LocalDateTime date;

    @ManyToMany(mappedBy = "meetings")
    private List<Merchant> merchants = new ArrayList<Merchant>();

    @ManyToMany(mappedBy = "meetings")
    private List<Client> clients = new ArrayList<Client>();

    @Column
    @ElementCollection(targetClass=String.class)
    private List<String> keywords = new ArrayList<>();

    @Column
    @ElementCollection(targetClass=Notification.class)
    private List<Notification> notifications = new ArrayList<>();

    @ElementCollection
    private List<String> wordCloud;

    @OneToMany(mappedBy = "meeting")
    private List<File> files;

    public Meeting() {
    }

    public Meeting(Long idMeeting,
                   String matter,
                   String description,
                   LocalDateTime date,
                   List<Merchant> merchants,
                   List<Client> clients,
                   List<String> keywords,
                   List<Notification> notifications,
                   List<String> wordCloud,
                   List<File> files) {
        this.idMeeting = idMeeting;
        this.matter = matter;
        this.description = description;
        this.date = date;
        this.merchants = merchants;
        this.clients = clients;
        this.keywords = keywords;
        this.notifications = notifications;
        this.wordCloud = wordCloud;
        this.files = files;
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

    public void removeMerchant(Merchant merchant) { this.merchants.remove(merchant); }

    public void addClient(Client client){
        this.clients.add(client);
    }

    public void removeClient(Client client) { this.clients.remove(client); }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword(String keyword){
        this.keywords.add(keyword);
    }

    public void removeKeyword(String keyword){
        this.keywords.remove(keyword);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<String> getWordCloud() {
        return wordCloud;
    }

    public void setWordCloud(List<String> wordCloud) {
        this.wordCloud = wordCloud;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void addFile(File file){
        this.files.add(file);
    }

    public void removeFile(File file){
        this.files.remove(file);
    }

    public File getFile(Long fileId){
        for(int i = 0; i < this.files.size(); i++){
            if(this.files.get(i).getIdFile().equals(fileId))
                return this.files.get(i);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(idMeeting, meeting.idMeeting) && Objects.equals(matter, meeting.matter) && Objects.equals(description, meeting.description) && Objects.equals(date, meeting.date) && Objects.equals(merchants, meeting.merchants) && Objects.equals(clients, meeting.clients) && Objects.equals(keywords, meeting.keywords) && Objects.equals(notifications, meeting.notifications) && Objects.equals(wordCloud, meeting.wordCloud) && Objects.equals(files, meeting.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeeting, matter, description, date, merchants, clients, keywords, notifications, wordCloud, files);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "idMeeting=" + idMeeting +
                ", matter='" + matter + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", merchants=" + merchants +
                ", clients=" + clients +
                ", keywords=" + keywords +
                ", notifications=" + notifications +
                ", wordCloud=" + wordCloud +
                ", files=" + files +
                '}';
    }
}
