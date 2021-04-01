package backend.mailsend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMessage(String to, String clientEmail, String notificationText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pymesapplication@gmail.com");
        message.setCc("Reminder for contact with client");
        message.setTo(to);
        message.setText(notificationText);
        javaMailSender.send(message);
    }
}
