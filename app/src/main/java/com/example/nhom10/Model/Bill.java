package com.example.nhom10.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bill implements Parcelable {
    private int billId; // ID của hóa đơn
    private int tableId; // ID của bàn
    private double totalAmount; // Tổng số tiền
    private String foodItemName; // Tên món ăn
    private String time; // Thời gian
    private String date; // Ngày

    // Constructor
    public Bill(int billId, int tableId, double totalAmount, String foodItemName, String time, String date) {
        this.billId = billId;
        this.tableId = tableId;
        this.totalAmount = totalAmount;
        this.foodItemName = foodItemName;
        this.time = time;
        this.date = date;
    }

    // Parcelable constructor
    protected Bill(Parcel in) {
        billId = in.readInt();
        tableId = in.readInt();
        totalAmount = in.readDouble();
        foodItemName = in.readString();
        time = in.readString();
        date = in.readString();
    }

    // Implement Parcelable methods
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(billId);
        dest.writeInt(tableId);
        dest.writeDouble(totalAmount);
        dest.writeString(foodItemName);
        dest.writeString(time);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    // Getter và Setter cho billId
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    // Getter và Setter cho tableId
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    // Getter và Setter cho totalAmount
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Getter và Setter cho foodItemName
    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    // Getter và Setter cho time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getter và Setter cho date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}