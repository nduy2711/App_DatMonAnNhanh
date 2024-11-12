package com.example.nhom10.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nhom10.Model.Bill;
import com.example.nhom10.Model.Employee;
import com.example.nhom10.Model.Category;
import com.example.nhom10.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String EMPLOYEE_TABLE = "Employee";
    private static final String ID_COL_EMPLOYEE = "EmployeeID";
    private static final String USERNAME_COL_EMPLOYEE = "Username";
    private static final String PASSWORD_COL_EMPLOYEE = "Password";
    private static final String FULLNAME_COL_EMPLOYEE = "FullName";
    private static final String EMAIL_COL_EMPLOYEE = "Mail";
    private static final String PHONE_COL_EMPLOYEE = "Phone";
    private static final String CCCD_COL_EMPLOYEE = "CCCD";

    private static final String TABLE_NAME = "RestaurantTable";
    private static final String ID_COL_TABLE = "TableID";
    private static final String STATUS_COL_TABLE = "Status";
    private static final String TIME_COL_TABLE = "Time";
    private static final String DATE_COL_TABLE = "Date";
    private static final String CUSTOMERNAME_COL_TABLE = "CustomerName";
    private static final String EMPLOYEEID_COL_TABLE = "EmployeeID";

    private static final String CATEGORY_TABLE = "Category";
    private static final String CATEGORY_ID = "CategoryID";
    private static final String CATEGORY_NAME = "CategoryName";

    private static final String MENU_ITEM_TABLE = "Item";
    private static final String MENU_ITEM_ID = "MenuItemID";
    private static final String CATEGORY_ID_FK = "CategoryID";
    private static final String ITEM_IMAGE_PATH = "ItemIdImage"; // Changed to "ItemIdImage" for image path or URL
    private static final String ITEM_NAME = "ItemName";
    private static final String PRICE = "Price";

    private static final String TABLE_BILL = "Bill";
    private static final String BILL_ID = "BillID"; // ID của hóa đơn
    private static final String TOTAL_AMOUNT = "TotalAmount"; // Tổng số tiền
    private static final String FOOD_ITEM_NAME = "FoodItemName"; // Tên món ăn
    private static final String TIME = "Time"; // Thời gian
    private static final String DATE = "Date"; // Ngày

    private static final String BILL_ITEM_TABLE = "Bill_Item";
    private static final String BILL_ITEM_ID = "BillItemID";
    private static final String BILL_ID_FK = "BillID";
    private static final String MENU_ITEM_ID_FK = "MenuItemID";
    private static final String QUANTITY = "Quantity";
    private static final String ITEM_PRICE = "Price";

    public DatabaseHandler(Context context) {
        super(context, "qlbh", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Employee
        String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + EMPLOYEE_TABLE + " ("
                + ID_COL_EMPLOYEE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME_COL_EMPLOYEE + " TEXT NOT NULL, "
                + PASSWORD_COL_EMPLOYEE + " TEXT NOT NULL, "
                + FULLNAME_COL_EMPLOYEE + " TEXT NOT NULL, "
                + EMAIL_COL_EMPLOYEE + " TEXT NOT NULL, "
                + PHONE_COL_EMPLOYEE + " TEXT NOT NULL, "
                + CCCD_COL_EMPLOYEE + " TEXT NOT NULL)";

        // Tạo bảng RestaurantTable
        String CREATE_TABLE_RESTAURANT_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STATUS_COL_TABLE + " TEXT NOT NULL, "
                + TIME_COL_TABLE + " TEXT, "
                + DATE_COL_TABLE + " TEXT, "
                + CUSTOMERNAME_COL_TABLE + " TEXT, "
                + EMPLOYEEID_COL_TABLE + " INTEGER, "
                + "FOREIGN KEY (" + EMPLOYEEID_COL_TABLE + ") REFERENCES " + EMPLOYEE_TABLE + "(" + ID_COL_EMPLOYEE + "))";

        // Tạo bảng Category
        String CREATE_TABLE_CATEGORY = "CREATE TABLE " + CATEGORY_TABLE + " ("
                + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT NOT NULL)";

        // Tạo bảng Item
        String CREATE_TABLE_ITEM = "CREATE TABLE " + MENU_ITEM_TABLE + " ("
                + MENU_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_ID_FK + " INTEGER, "
                + ITEM_NAME + " TEXT NOT NULL, "
                + PRICE + " REAL NOT NULL, "
                + ITEM_IMAGE_PATH + " TEXT, " // Store image as String (URL or path)
                + "FOREIGN KEY (" + CATEGORY_ID_FK + ") REFERENCES " + CATEGORY_TABLE + "(" + CATEGORY_ID + "))";

        String CREATE_BILL_TABLE = "CREATE TABLE " + TABLE_BILL + " (" +
                BILL_ID + " LONG PRIMARY KEY, " +
                TOTAL_AMOUNT + " REAL, " +
                TIME + " TEXT, " +
                DATE + " TEXT)";

        // Tạo bảng BILL_ITEM_TABLE
        String CREATE_BILL_ITEM_TABLE = "CREATE TABLE " + BILL_ITEM_TABLE + " (" +
                BILL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BILL_ID_FK + " INTEGER, " +
                MENU_ITEM_ID_FK + " INTEGER, " +
                QUANTITY + " INTEGER, " +
                ITEM_PRICE + " REAL, " +
                FOOD_ITEM_NAME + " TEXT, " + // Thêm tên món ăn vào bảng này
                "FOREIGN KEY (" + BILL_ID_FK + ") REFERENCES " + TABLE_BILL + "(" + BILL_ID + "), " +
                "FOREIGN KEY (" + MENU_ITEM_ID_FK + ") REFERENCES " + MENU_ITEM_TABLE + "(" + MENU_ITEM_ID + "))";

        // Execute the table creation statements
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        db.execSQL(CREATE_TABLE_RESTAURANT_TABLE);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_BILL_TABLE);
        db.execSQL(CREATE_BILL_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu nó tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MENU_ITEM_TABLE);

        // Gọi lại onCreate để tạo lại các bảng mới
        onCreate(db);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE + " WHERE " + USERNAME_COL_EMPLOYEE + "=? AND " + PASSWORD_COL_EMPLOYEE + "=?", new String[]{username, password});
        boolean loggedIn = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return loggedIn;
    }

    public void insertEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FULLNAME_COL_EMPLOYEE, employee.getFullName());
        values.put(EMAIL_COL_EMPLOYEE, employee.getEmail());
        values.put(PHONE_COL_EMPLOYEE, employee.getPhone());
        values.put(CCCD_COL_EMPLOYEE, employee.getCccd());
        values.put(USERNAME_COL_EMPLOYEE, employee.getUsername());
        values.put(PASSWORD_COL_EMPLOYEE, employee.getPassword());
        db.insert(EMPLOYEE_TABLE, null, values);
        db.close();
    }

    public void insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, category.getCategoryId());
        values.put(CATEGORY_NAME, category.getCategoryName());
        db.insert(CATEGORY_TABLE, null, values);
        db.close();
    }

    public void insertItem(Product product) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở kết nối với database
        ContentValues values = new ContentValues();

        values.put(MENU_ITEM_ID, product.getMenuItemId()); // Thêm ID của món ăn
        values.put(CATEGORY_ID_FK, product.getCategoryIdFk()); // Thêm ID của danh mục (khóa ngoại)
        values.put(ITEM_NAME, product.getName()); // Thêm tên món ăn
        values.put(PRICE, product.getPrice()); // Thêm giá món ăn
        values.put(ITEM_IMAGE_PATH, product.getImage()); // Thêm đường dẫn hoặc URL hình ảnh

        // Chèn vào bảng MENU_ITEM_TABLE
        db.insert(MENU_ITEM_TABLE, null, values);

        // Đóng kết nối với database
        db.close();
    }

    public void insertBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở kết nối với database
        ContentValues values = new ContentValues();

        values.put(BILL_ID, bill.getBillId()); // Thêm ID của hóa đơn
        values.put(TOTAL_AMOUNT, bill.getTotalAmount()); // Thêm tổng số tiền
        values.put(TIME, bill.getTime()); // Thêm thời gian
        values.put(DATE, bill.getDate()); // Thêm ngày

        // Chèn vào bảng Bill
        db.insert(TABLE_BILL, null, values);

        // Đóng kết nối với database
        db.close();
    }

    // Check if a specific table is empty
    private boolean isTableEmpty(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        cursor.moveToFirst();
        boolean isEmpty = cursor.getInt(0) == 0;
        cursor.close();
        db.close();
        return isEmpty;
    }

    public void insertBillItem(long billId, long menuItemId, int quantity, double price, String foodItemName) {
        // Thực hiện chèn dữ liệu vào database với tên món ăn
        ContentValues contentValues = new ContentValues();
        contentValues.put(BILL_ID_FK, billId); // Chèn ID hóa đơn
        contentValues.put(MENU_ITEM_ID_FK, menuItemId); // Chèn ID món ăn
        contentValues.put(QUANTITY, quantity); // Chèn số lượng món ăn
        contentValues.put(ITEM_PRICE, price); // Chèn giá món ăn
        contentValues.put(FOOD_ITEM_NAME, foodItemName); // Chèn tên món ăn vào ContentValues

        // Chèn vào BILL_ITEM_TABLE
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(BILL_ITEM_TABLE, null, contentValues);

        // Đóng kết nối với database
        db.close();
    }

    // Initialize data only if tables are empty
    public void initData() {
        if (isTableEmpty(EMPLOYEE_TABLE)) {
            insertEmployee(new Employee(1, "nhanvien1", "123", "Nguyễn Công Phượng", "congphuong@gmail.com", "012356789", "CCCD123"));
            insertEmployee(new Employee(2, "nhanvien2", "123", "Nguyễn Tiến Linh", "tienlinh@gmail.com", "1234556788", "CCCD456"));
        }

        if (isTableEmpty(CATEGORY_TABLE)) {
            insertCategory(new Category(1, "Thịt nướng"));
            insertCategory(new Category(2, "Tokbokki"));
            insertCategory(new Category(3, "Lẩu"));
            insertCategory(new Category(4, "Ăn vặt"));
        }

        if (isTableEmpty(MENU_ITEM_TABLE)) {
            insertItem(new Product(1, 1, "Ba rọi", "baroi", 100000));
            insertItem(new Product(2, 1, "Ba chỉ bò", "bachibo", 120000));
            insertItem(new Product(3, 1, "Thăng bò", "thangbo", 130000));
            insertItem(new Product(4, 1, "Ba chỉ cuộn nấm", "bachibocuon", 140000));

            insertItem(new Product(5, 2, "Tokbokki truyền thống", "tokbokkitruyenthong", 90000));
            insertItem(new Product(6, 2, "Tokbokki sốt cay", "tokbokkisotcay", 95000));
            insertItem(new Product(7, 2, "Tokbokki sốt phô mai", "tokbokkisotphomai", 85000));
            insertItem(new Product(8, 2, "Tokbokki chiên", "tokbokkichienxu", 100000));

            insertItem(new Product(9, 3, "Lẩu bò", "laubo", 200000));
            insertItem(new Product(10, 3, "Lẩu kim chi", "laukimchi", 190000));
            insertItem(new Product(11, 3, "Lẩu nấm", "launam", 210000));
            insertItem(new Product(12, 3, "Lẩu tokbokki", "lautokbokki", 220000));

            insertItem(new Product(13, 4, "Chả cá hàn quốc", "chacahanquoc", 50000));
            insertItem(new Product(14, 4, "Bánh mì trung quốc", "banhmitrunghanquoc", 45000));
            insertItem(new Product(15, 4, "Manbu", "manbu", 55000));
            insertItem(new Product(16, 4, "Dòi hàn quốc", "doihanquoc", 60000));
        }

        if (isTableEmpty(TABLE_BILL)) {
            insertBill(new Bill(1, 100000, "12:00", "2024-11-01"));
            insertBill(new Bill(5, 90000, "13:00", "2024-10-01"));
            insertBill(new Bill(9, 200000, "19:00", "2024-10-01"));
            insertBill(new Bill(13, 50000, "20:00", "2024-11-02"));
            insertBill(new Bill(6, 95000, "18:30", "2024-10-03"));
            insertBill(new Bill(12, 220000, "19:00", "2024-11-03"));
            insertBill(new Bill(14, 45000, "15:00", "2024-11-04"));
            insertBill(new Bill(16, 60000, "17:45", "2024-11-05"));
        }

        if (isTableEmpty(BILL_ITEM_TABLE)) {
            // Insert Bill Items with the updated parameters
            insertBillItem(1, 1, 2, 100000, "Ba rọi"); // Bill 1: 2 Ba rọi, 100000 each
            insertBillItem(2, 5, 1, 90000, "Tokbokki truyền thống"); // Bill 2: 1 Tokbokki truyền thống, 90000 each
            insertBillItem(3, 9, 1, 200000, "Lẩu bò"); // Bill 3: 1 Lẩu bò, 200000 each
            insertBillItem(4, 13, 1, 50000, "Chả cá hàn quốc"); // Bill 4: 1 Chả cá hàn quốc, 50000 each
            insertBillItem(5, 6, 1, 95000, "Tokbokki sốt cay"); // Bill 5: 1 Tokbokki sốt cay, 95000 each
            insertBillItem(6, 12, 1, 220000, "Lẩu tokbokki"); // Bill 6: 1 Lẩu tokbokki, 220000 each
            insertBillItem(7, 14, 1, 45000, "Bánh mì trung quốc"); // Bill 7: 1 Bánh mì trung quốc, 45000 each
            insertBillItem(8, 16, 1, 60000, "Dòi hàn quốc"); // Bill 8: 1 Dòi hàn quốc, 60000 each

            // Insert more bill items for the new bill with billId 9
            insertBillItem(9, 12, 1, 220000, "Lẩu tokbokki"); // Bill 9: 1 Lẩu tokbokki, 220000 each
            insertBillItem(9, 1, 2, 100000, "Ba rọi"); // Bill 9: 2 Ba rọi, 100000 each
            insertBillItem(9, 16, 1, 60000, "Dòi hàn quốc"); // Bill 9: 1 Dòi hàn quốc, 60000 each
        }
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }

    public int getMaxMenuItemID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(menuItemID) FROM Item", null);
        int maxId = 0;
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        cursor.close();
        return maxId;
    }

    public List<Bill> getBillsForMonthYear(int month, int year) {
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Sử dụng cú pháp SQL để tìm các hóa đơn trong tháng và năm cụ thể
        String query = "SELECT * FROM " + TABLE_BILL + " WHERE strftime('%m', " + DATE + ") = ? AND strftime('%Y', " + DATE + ") = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.format("%02d", month), String.valueOf(year)});

        if (cursor.moveToFirst()) {
            do {
                int billId = cursor.getInt(cursor.getColumnIndexOrThrow(BILL_ID));
                double totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(TOTAL_AMOUNT));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(TIME));
                String dateValue = cursor.getString(cursor.getColumnIndexOrThrow(DATE));

                Bill bill = new Bill(billId, totalAmount, time, dateValue);
                bills.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bills;
    }



}
