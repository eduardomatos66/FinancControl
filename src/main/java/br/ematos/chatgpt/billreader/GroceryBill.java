package br.ematos.chatgpt.billreader;

class GroceryBill {
  private String storeName;
  private String date;
  private double totalAmount;
  private GroceryItem[] items;

  public GroceryBill(String storeName, String date, double totalAmount, GroceryItem[] items) {
    this.storeName = storeName;
    this.date = date;
    this.totalAmount = totalAmount;
    this.items = items;
  }

  // Getters and setters

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Store: ").append(storeName).append("\n");
    sb.append("Date: ").append(date).append("\n");
    sb.append("Total Amount: $").append(totalAmount).append("\n");
    sb.append("Items:\n");
    for (GroceryItem item : items) {
      sb.append(item).append("\n");
    }
    return sb.toString();
  }
}
