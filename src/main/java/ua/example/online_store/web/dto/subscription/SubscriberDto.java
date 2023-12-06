package ua.example.online_store.web.dto.subscription;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriberDto {

  @NotNull
  private Long subscriptionId;
  @NotNull
  @NotEmpty
  @Email
  private String email;

}
