package com.example.nhom10.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bill {
    private long billId; // ID của hóa đơn
    private double totalAmount; // Tổng số tiền
    private String time; // Thời gian
    private String date; // Ngày

    // Constructor
    public Bill(long billId, double totalAmount, String time, String date) {
        this.billId = billId;
        this.totalAmount = totalAmount;
        this.time = time;
        this.date = date;
    }

    public Bill() {}

    // Parcelable constructor
    protected Bill(Parcel in) {
        billId = in.readLong();  // Corrected to readLong() for long data type
        totalAmount = in.readDouble();
        time = in.readString();
        date = in.readString();
    }

    public static final Parcelable.Creator<Bill> CREATOR = new Parcelable.Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    // Getter and Setter for billId
    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    // Getter and Setter for totalAmount
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Getter and Setter for time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getter and Setter for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
