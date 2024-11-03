package com.example.nhom10.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int menuItemId;   // Thuộc tính MenuItemID dạng int
    private int categoryIdFk; // Thuộc tính CategoryID (khóa ngoại) dạng int
    private String name;      // Tên món ăn
    private String image;     // URL hoặc đường dẫn hình ảnh của món ăn
    private double price;     // Giá của món ăn

    // Constructor
    public Product(int menuItemId, int categoryIdFk, String name, String image, double price) {
        this.menuItemId = menuItemId;
        this.categoryIdFk = categoryIdFk;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    // Parcelable constructor
    protected Product(Parcel in) {
        menuItemId = in.readInt();
        categoryIdFk = in.readInt();
        name = in.readString();
        image = in.readString();
        price = in.readDouble();
    }

    // Implement Parcelable methods
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(menuItemId);
        dest.writeInt(categoryIdFk);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeDouble(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // Getter và Setter cho menuItemId
    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    // Getter và Setter cho categoryIdFk
    public int getCategoryIdFk() {
        return categoryIdFk;
    }

    public void setCategoryIdFk(int categoryIdFk) {
        this.categoryIdFk = categoryIdFk;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter và Setter cho price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
