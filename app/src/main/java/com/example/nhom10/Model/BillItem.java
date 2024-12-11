package com.example.nhom10.Model;

public class BillItem {
    private int billItemId;
    private long billId;
    private int menuItemId;
    private int quantity;
    private double itemPrice;
    private String foodItemName;

    // Constructor
    public BillItem(int billItemId, long billId, int menuItemId, int quantity, double itemPrice, String foodItemName) {
        this.billItemId = billItemId;
        this.billId = billId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.foodItemName = foodItemName;
    }

    // Getters and Setters
    public int getBillItemId() {
        return billItemId;
    }

    public void setBillItemId(int billItemId) {
        this.billItemId = billItemId;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }
}
