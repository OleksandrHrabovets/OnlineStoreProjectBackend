package ua.example.online_store.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribingEmailService implements EmailService {

  private final JavaMailSender mailSender;

  @Override
  public void sendEmail(String email, String title, String textMessage) {

    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(email);
      helper.setSubject(title);
      helper.setText(textMessage);
    } catch (MessagingException e) {
      log.error("Failed to send email to {}", email, e);
      return;
    }
    mailSender.send(message);
    log.info("Email to {} sent successfully", email);
  }
}
