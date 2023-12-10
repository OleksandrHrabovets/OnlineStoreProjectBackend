package ua.example.online_store.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribingEmailService implements EmailService {

  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String sender;

  @Override
  public void sendEmail(String email, String title, String textMessage) {

    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(String.format("Online store project <%s>", sender));
      helper.setTo(email);
      helper.setSubject(title);
      if (textMessage.startsWith("<!DOCTYPE html>")) {
        helper.setText(textMessage, true);
      } else {
        helper.setText(textMessage);
      }
      mailSender.send(message);
      log.info("Email to {} sent successfully", email);
    } catch (MessagingException e) {
      log.error("Failed to send email to {}", email, e);
    }
  }
}
