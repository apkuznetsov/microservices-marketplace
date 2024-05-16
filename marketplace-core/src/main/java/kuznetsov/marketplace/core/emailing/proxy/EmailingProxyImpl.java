package kuznetsov.marketplace.core.emailing.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailingProxyImpl implements EmailingProxy {

    private final JavaMailSender emailSender;
    @Value("${emailing.username}")
    private String username;

    @Autowired
    public EmailingProxyImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    public void send(String to, String subject, String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom(username);
        emailMessage.setTo(to);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        emailSender.send(emailMessage);
    }
}
