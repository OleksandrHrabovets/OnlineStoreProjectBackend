package ua.example.online_store.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDeliveryDto {

  private Long id;
  @Email
  private String email;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  private String customerPhoneNumber;
  @NotBlank
  private String address;

}
