package com.example.nhom10.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.nhom10.Model.Category;
import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

import java.util.ArrayList;

public class ProductHandler extends SQLiteOpenHelper {
    // Table Category
    private static final String CATEGORY_TABLE = "Category";
    private static final String CATEGORY_ID = "CategoryID";
    private static final String CATEGORY_NAME = "CategoryName";

    private static final String MENU_ITEM_TABLE = "Item";
    private static final String MENU_ITEM_ID = "MenuItemID";
    private static final String CATEGORY_ID_FK = "CategoryID";
    private static final String ITEM_IMAGE_PATH = "ItemIdImage"; // Changed to "ItemIdImage" for image path or URL
    private static final String ITEM_NAME = "ItemName";
    private static final String PRICE = "Price";

    public ProductHandler (Context context) {
        super(context, "qlbh", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public ArrayList<Product> getProductsByCategory(int categoryId) {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MENU_ITEM_TABLE + " WHERE " + CATEGORY_ID_FK + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                int menuItemId = cursor.getInt(cursor.getColumnIndexOrThrow(MENU_ITEM_ID)); // MenuItemID
                int category = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY_ID_FK)); // CategoryID
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME)); // Item name
                String image = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_IMAGE_PATH)); // Image path or URL as String
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)); // Item price

                // Create Product object with String image ID
                productList.add(new Product(menuItemId, category, name, image, price));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }



}