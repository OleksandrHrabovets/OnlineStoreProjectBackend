package ua.example.online_store;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(version = "${my.api.version}",
    title = "${my.api.title}", description = "${my.api.description}"))
@EnableScheduling
public class OnlineStoreProjectBackendApplication {

  public static void main(String[] args) {

    SpringApplication.run(OnlineStoreProjectBackendApplication.class, args);

  }

}
