package backend.scheduler;

import backend.mailsend.EmailService;
import backend.meetings.MeetingRepository;
import backend.notification.Notification;
import backend.notification.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class NotificationScheduler {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailBuilder emailBuilder;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 25 03 * * ?") //Execute at 12 PM every day
    @Transactional
    public void sendNotifications(){
        notificationRepository.deleteAll(); //DELETE THIS LINE
        List<Long[]> notificationsToSend = meetingRepository.getNotificationsToSend(); //
        // Per each notification we have to send it to merchant's email and store it in notification table in order to send it only once.
        notificationsToSend.forEach(notification -> {
            /*
            * notification[0] = id_meeting
            * notification[1] = id_merchant
            * notification[2] = id_client
            * */
            Long idMeeting = notification[0], idMerchant = notification[1], idClient = notification[2];
            String[] emails = emailBuilder.searchEmails(idMerchant, idClient);
            /*
            * emails[0] = merchant email
            * emails[1] = client email
            * */
            emailService.sendMessage(emails[0], emails[1], emailBuilder.buildNotification(idMeeting, idMerchant, idClient));
            //Now we have to store that the given merchant has been notified.
            notificationRepository.save(new Notification(idMeeting, idMerchant, idClient));
        });
    }

}
