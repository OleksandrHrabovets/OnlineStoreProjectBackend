package ua.example.online_store.service.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

  void sendEmail(String email, String title, String message);
}
