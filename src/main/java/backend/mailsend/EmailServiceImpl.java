package backend.mailsend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMessage(String to, String clientEmail, String notificationText) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("pymesapplication@gmail.com");
        helper.setSubject("Reminder for contact with client");
        helper.setTo(to);
        helper.setText(notificationText, true);
        javaMailSender.send(mimeMessage);
    }
}
