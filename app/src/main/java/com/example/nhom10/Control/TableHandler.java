package com.example.nhom10.Control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TableHandler extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "RestaurantTable";
    private static final String ID_COL_TABLE = "TableID";
    private static final String STATUS_COL_TABLE = "Status";
    private static final String TIME_COL_TABLE = "Time";
    private static final String DATE_COL_TABLE = "Date";
    private static final String CUSTOMERNAME_COL_TABLE = "CustomerName";
    private static final String EMPLOYEEID_COL_TABLE = "EmployeeID";

    public TableHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
