package com.example.nhom10.Model;

public class BookedTable {
    private String customerName;
    private String phone;
    private String email;
    private String bookingDate;
    private String bookingTime;

    public BookedTable() {
    }

    public BookedTable(String customerName, String phone, String email, String bookingDate, String bookingTime) {
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

}
