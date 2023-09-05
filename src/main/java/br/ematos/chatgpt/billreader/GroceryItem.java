package br.ematos.chatgpt.billreader;

class GroceryItem {
  private String name;
  private double price;

  public GroceryItem(String name, double price) {
    this.name = name;
    this.price = price;
  }

  // Getters and setters

  @Override
  public String toString() {
    return "Item: " + name + ", Price: $" + price;
  }
}
