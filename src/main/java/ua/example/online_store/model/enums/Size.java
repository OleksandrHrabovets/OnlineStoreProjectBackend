package ua.example.online_store.model.enums;

import java.util.Arrays;

public enum Size {

  XS("XS"),
  S("S"),
  M("M"),
  L("L"),
  XL("XL"),
  TWO_XL("2XL"),
  THREE_XL("3XL");

  private final String value;

  Size(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Size getSize(String value) {
    return Arrays.stream(values())
        .filter(v -> v.getValue().equalsIgnoreCase(value))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            "Can not map value of skuCharacteristic %s".formatted(value)));
  }

}