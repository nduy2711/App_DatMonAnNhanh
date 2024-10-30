package com.example.nhom10.Control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.nhom10.Model.Employee;

public class LoginHandler extends SQLiteOpenHelper {


    private static final String TABLE_NAME = "Employee";
    private static final String ID_COL_EMPLOYEE = "EmployeeID";
    private static final String USERNAME_COL_EMPLOYEE = "Username";
    private static final String PASSWORD_COL_EMPLOYEE = "Password";
    private static final String FULLNAME_COL_EMPLOYEE = "FullName";
    private static final String EMAIL_COL_EMPLOYEE = "Mail";
    private static final String PHONE_COL_EMPLOYEE = "Phone";
    private static final String CCCD_COL_EMPLOYEE = "CCCD";

    public LoginHandler (Context context) {
        super(context, "qlbh", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean checkLogin(String username, String password) {
        // Get a readable database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to check for username and password
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME_COL_EMPLOYEE + " = ? AND " + PASSWORD_COL_EMPLOYEE + " = ?",
                new String[]{username, password}
        );

        // Check if any result was returned
        boolean loggedIn = cursor.getCount() > 0;

        // Close the cursor and database
        cursor.close();
        db.close();

        return loggedIn; // Return the login result
    }

}
