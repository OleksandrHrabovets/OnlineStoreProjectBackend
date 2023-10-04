package ua.example.online_store.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoDto {

  private Long id;
  private String url;

}
