package backend.notification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "notification")
@IdClass(NotificationId.class)
public class Notification implements Serializable {
    @Id
    @Column(name = "id_meeting")
    private Long idMeeting;
    @Id
    @Column(name = "id_merchant")
    private Long idMerchant;
    @Id
    @Column(name = "id_client")
    private Long idClient;

    public Long getIdMerchant() {
        return idMerchant;
    }

    public Notification() {
    }

    public Notification(Long idMeeting, Long idMerchant, Long idClient) {
        this.idMeeting = idMeeting;
        this.idMerchant = idMerchant;
        this.idClient = idClient;
    }

    public Long getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(Long idMeeting) {
        this.idMeeting = idMeeting;
    }

    public void setIdMerchant(Long idMerchant) {
        this.idMerchant = idMerchant;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(idMeeting, that.idMeeting) && Objects.equals(idMerchant, that.idMerchant) && Objects.equals(idClient, that.idClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeeting, idMerchant, idClient);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idMeeting=" + idMeeting +
                ", idMerchant=" + idMerchant +
                ", idClient=" + idClient +
                '}';
    }
}
