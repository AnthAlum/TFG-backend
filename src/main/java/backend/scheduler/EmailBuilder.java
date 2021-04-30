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
        if(merchant == null || client == null)
            return new String[0];
        String[] emails = new String[2];
        emails[0] = merchant.getEmail();
        emails[1] = client.getEmail();
        return emails;
    }


    public String buildNotificationBody(Long idMeeting, Long idMerchant, Long idClient) throws Exception {
        Meeting meeting = meetingRepository.findById(idMeeting).orElse(null);
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        Client client = clientRepository.findById(idClient).orElse(null);
        if(merchant == null || client == null || meeting == null)
            return null;
        return "<div fxFlex=\"col\" style=\"font-family:'Montserrat',sans-serif;font-size:18px;min-width: 100%;\">\n" +
                "  <div style=\"width:450px;margin:auto;padding:20px 60px 10px 60px;background-color:#f8f8f8;font-size:14px;\">\n" +
                "    <strong>Hi " + merchant.getName() + "!</strong><br><br>\n" +
                "      You have not been in touch with " + client.getName() + " since " + meeting.getDate().toLocalDate() + ".<br>\n" +
                "      You talked about:<br>\n" +
                "      <ul>\n" +
                "        <li><b>Description</b>: " + check(meeting.getDescription(), "Description not defined") + "</li>\n" +
                "        <li><b>Keywords</b>: " + check(String.join(", ", meeting.getKeywords()), "Keywords not defined") + "</li>\n" +
                "        <li><u><b>Client info:</b></u>\n" +
                "          <ul style=\"padding-top: 8px;\">\n" +
                "            <li><b>Name</b>: " + client.getName() + "</li>\n" +
                "                <li><b>Email</b>: " + client.getEmail() + "</li> \n" +
                "                <li><b>Phone</b>: " + client.getPhone() + "</li> \n" +
                "                <li><b>Company</b>: " + client.getCompany() + "</li> \n" +
                "          </ul>\n" +
                "        </li>\n" +
                "      </ul>\n" +
                "  </div>\n" +
                "  <div style=\"background-color: #031647;color:white;min-height: 50px;padding:10px 0px;\" align=\"center\">\n" +
                "    Please Contact with him/her!\n" +
                "    <div style=\"font-size:12px;padding: 8px;\">\n" +
                "      Stay Updated \n" +
                "    </div>\n" +
                "    <div style=\"border-bottom-width: 2px;border-bottom-color:white;border-bottom-style:solid;padding-top:5px;width:60%;margin:0 auto\"></div>\n" +
                "    <p style=\"font-family:'Montserrat',sans-serif,Arial;font-size:12px;line-height:18px\" align=\"center\">\n" +
                "        <em>Copyright @ 2021 PYMES Application, All rights reserved. </em>\n" +
                "    </p>\n" +
                "  </div>\n" +
                "</div>";
        /*"<div fxFlex=\"col\" style=\"font-family:'Montserrat',sans-serif;font-size:18px;min-width: 100%;\">\n" +
                "  <div style=\"width:450px;margin:auto;padding:20px 60px 10px 60px;background-color:#f8f8f8;font-size:14px;\">\n" +
                "      <strong>Hi " + merchant.getName() + "!</strong><br><br>\n" +
                "      You have not been in touch with " + client.getName() + " since " + meeting.getDate().toLocalDate() + ".<br>\n" +
                "      You talked about:<br>\n" +
                "      <ul>\n" +
                "        <li><b>Description</b>: " + check(meeting.getDescription(), "Description not defined") +" </li>\n" +
                "        <li><b>Keywords</b>:" + check(String.join(", ", meeting.getKeywords()), "Keywords not defined") + "</li>\n" +
                "        <li><u><b>Client info:</b></u>\n" +
                "          <ul style=\"padding-top: 8px;\">\n" +
                "            <li><b>Name</b>: " + client.getName() + "</li>\n" +
                "            <li><b>Email</b>: " + client.getEmail() + "</li>\n" +
                "            <li><b>Phone</b>: " + client.getPhone() + "</li>\n" +
                "            <li><b>Company</b>: " + client.getCompany() + "</li>\n" +
                "          </ul>\n" +
                "        </li>\n" +
                "      </ul>\n" +
                "  </div>\n" +
                "  <div style=\"background-color: #031647;color:white;min-height: 50px;padding:10px 0px;\" align=\"center\">\n" +
                "    Please Contact with him/her!\n" +
                "    <div style=\"font-size:12px;padding: 8px;\">\n" +
                "      Stay Updated \n" +
                "    </div>\n" +
                "    <div style=\"border-bottom-width: 2px;border-bottom-color:white;border-bottom-style:solid;padding-top:5px;width:60%;margin:0 auto\"></div>\n" +
                "    <p style=\"font-family:'Montserrat',sans-serif,Arial;font-size:12px;line-height:18px\" align=\"center\">\n" +
                "        <em>Copyright @ 2021 PYMES Application, All rights reserved. </em>\n" +
                "    </p>\n" +
                "  </div>\n" +
                "</div>";*/
    }

    public String check(String value, String defaultMessage){
        if(value == null)
            return defaultMessage;
        return value;
    }
}
