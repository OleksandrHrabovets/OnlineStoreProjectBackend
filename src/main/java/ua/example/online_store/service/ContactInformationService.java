package ua.example.online_store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.example.online_store.service.email.EmailService;
import ua.example.online_store.web.dto.ContactInformationEmailDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactInformationService {

  public static final String INVOKED_METHOD = "invoked method {}";
  @Value("${app.email-sales-manager}")
  private String emailSalesManager;
  private final EmailService emailService;

  public void sendEmail(ContactInformationEmailDto contactInformationEmailDto) {
    log.info(INVOKED_METHOD, "sendEmail()");
    emailService.sendEmail(
        emailSalesManager,
        "Повідомлення від користувача",
        """
            Користувач надіслав контактну інформацію:
            Ім'я: %s
            Email: %s
            Повідомлення: %s
            """.formatted(
            contactInformationEmailDto.getName(),
            contactInformationEmailDto.getEmail(),
            contactInformationEmailDto.getTextMessage())
    );
  }

}