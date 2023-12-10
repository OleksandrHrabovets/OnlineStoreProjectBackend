package ua.example.online_store.web.dto;

import java.math.BigDecimal;

public record OrderItemRecord(String title, String color, String size, String photoUrl,
                              BigDecimal price, BigDecimal quantity, BigDecimal amount) {

}
