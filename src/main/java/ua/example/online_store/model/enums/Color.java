package ua.example.online_store.model.enums;

import java.util.Arrays;

public enum Color {

  RED("#FF0000"),
  GREEN("#008000"),
  BLUE("#0000FF"),
  YELLOW("#FFFF00"),
  ORANGE("#FFA500"),
  GOLD("#FFD700"),
  GREY("#808080"),
  BROWN("#964B00"),
  PURPLE("#800080"),
  BLACK("#000000"),
  WHITE("#FFFFFF"),
  MILKY("#FFF5EC");

  private final String value;

  Color(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Color getColor(String value) {
    return Arrays.stream(values())
        .filter(v -> v.name().equalsIgnoreCase(value))
        .findAny()
        .orElse(getColorByValue(value));
  }

  public static Color getColorByValue(String value) {
    return Arrays.stream(values())
        .filter(v -> v.getValue().equalsIgnoreCase(value))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            "Can not map value of skuCharacteristic %s".formatted(value)));
  }
}
