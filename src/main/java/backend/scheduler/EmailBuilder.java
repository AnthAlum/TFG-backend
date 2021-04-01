package backend.scheduler;

import backend.clients.Client;
import backend.clients.ClientRepository;
import backend.meetings.Meeting;
import backend.meetings.MeetingRepository;
import backend.merchants.Merchant;
import backend.merchants.MerchantRepository;
import backend.notification.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailBuilder {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public String[] searchEmails(Long idMerchant, Long idClient){
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        Client client = clientRepository.findById(idClient).orElse(null);
        String[] emails = new String[2];
        emails[0] = merchant.getEmail();
        emails[1] = client.getEmail();
        return emails;
    }


    public String buildNotification(Long idMeeting, Long idMerchant, Long idClient){
        Meeting meeting = meetingRepository.findById(idMeeting).orElse(null);
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        Client client = clientRepository.findById(idClient).orElse(null);
        String notification = "Hi " + merchant.getName() + "!\n" +
                "You have not been un touch with " + client.getName() + " since " + meeting.getDate().toLocalDate() + ".\n" +
                "\n" +
                "You talked about:\n" +
                "\nDescription: " +
                check(meeting.getDescription(), "Description not defined")  + "\nMeeting keywords: " +
                check(String.join(", ", meeting.getKeywords()), "Keywords not defined") + "\n" +
                "Client info:\nEmail: " +
                client.getEmail() + "\nPhone: " +
                client.getPhone() + "\nCompany: " +
                client.getCompany() + "\n" +
                "Please Contact with him/her!";
        return notification;
    }

    public String check(String value, String defaultMessage){
        if(value == null)
            return defaultMessage;
        return value;
    }
}
