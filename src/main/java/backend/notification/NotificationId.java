package backend.notification;

import java.io.Serializable;
import java.util.Objects;

public class NotificationId implements Serializable {
    private Long idMeeting;
    private Long idMerchant;
    private Long idClient;

    public NotificationId() {
    }

    public NotificationId(Long idMeeting, Long idMerchant, Long idClient) {
        this.idMeeting = idMeeting;
        this.idMerchant = idMerchant;
        this.idClient = idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationId that = (NotificationId) o;
        return Objects.equals(idMeeting, that.idMeeting) && Objects.equals(idMerchant, that.idMerchant) && Objects.equals(idClient, that.idClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeeting, idMerchant, idClient);
    }
}
