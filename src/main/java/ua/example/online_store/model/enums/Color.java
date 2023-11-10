package ua.example.online_store.model.enums;

import java.util.Arrays;

public enum Color {

  RED,
  GREEN,
  BLUE,
  YELLOW,
  ORANGE,
  GOLD,
  GREY,
  BROWN,
  PURPLE,
  BLACK,
  WHITE,
  MILKY;

  public static Color getColor(String value) {
    return Arrays.stream(values())
        .filter(v -> v.name().equalsIgnoreCase(value))
        .findAny()
        .orElseThrow(IllegalArgumentException::new);
  }

}
