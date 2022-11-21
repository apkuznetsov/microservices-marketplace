package kuznetsov.marketplace.services.emailing.configuration;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailingConfiguration {

  @Value("${emailing.protocol}")
  private String protocol;
  @Value("${emailing.host}")
  private String host;
  @Value("${emailing.port}")
  private int port;
  @Value("${emailing.username}")
  private String username;
  @Value("${emailing.password}")
  private String password;

  @Bean
  public JavaMailSender getEmailSender() {
    JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
    emailSender.setHost(host);
    emailSender.setUsername(username);
    emailSender.setPassword(password);
    emailSender.setPort(port);

    Properties properties = emailSender.getJavaMailProperties();
    properties.setProperty("mail.transport.protocol", protocol);

    return emailSender;
  }

}
