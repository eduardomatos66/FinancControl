package br.ematos.chatgpt.calculator;

public enum Unit {
  G("g"),
  KG("kg"),
  LB("lb"),
  ML("ml"),
  L("L"),
  OZ("oz"),
  CM("cm"),
  MM("mm"),
  M("m");

  private final String label;

  Unit(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static Unit valueOfUnitName(String unitName) {
    for (Unit unit : values()) {
      if (unit.getLabel().equalsIgnoreCase(unitName)) {
        return unit;
      }
    }
    throw new IllegalArgumentException("No such Unit with unitName: " + unitName);
  }
}
