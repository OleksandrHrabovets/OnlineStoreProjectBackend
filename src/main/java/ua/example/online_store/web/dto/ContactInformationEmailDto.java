package ua.example.online_store.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInformationEmailDto {

  @NotEmpty
  private String name;
  @NotNull
  @NotEmpty
  @Email
  private String email;
  @NotEmpty
  private String textMessage;

}
