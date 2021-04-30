package backend.mailsend;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendMessage(String to, String clientEmail, String notificationText) throws Exception;
}
