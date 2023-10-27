package ua.example.online_store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcApplicationConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    String crossOrigins = "*";
    registry.addMapping("/api/**")
        .allowedOrigins(crossOrigins)
        .maxAge(3600);
  }

}