package ua.example.online_store.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.example.online_store.service.ContactInformationService;
import ua.example.online_store.web.dto.ContactInformationEmailDto;

@Slf4j
@RestController
@RequestMapping("/api/v1/contact_information")
@RequiredArgsConstructor
public class ContactInformationController {

  public static final String INVOKED_METHOD = "invoked method {}";

  private final ContactInformationService contactInformationService;

  @PostMapping("/send_email")
  @ResponseStatus(HttpStatus.OK)
  public void sendEmail(
      @Valid @RequestBody ContactInformationEmailDto contactInformationEmailDto) {
    log.info(INVOKED_METHOD, "sendEmail()");
    contactInformationService.sendEmail(contactInformationEmailDto);
  }

}
