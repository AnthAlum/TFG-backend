package backend.notification;

import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, NotificationId> {
}
