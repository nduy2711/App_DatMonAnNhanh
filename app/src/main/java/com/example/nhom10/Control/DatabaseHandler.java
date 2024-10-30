package com.example.nhom10.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nhom10.Model.Employee;
import com.example.nhom10.Model.Category;
import com.example.nhom10.Model.Product;

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

        // Execute the table creation statements
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        db.execSQL(CREATE_TABLE_RESTAURANT_TABLE);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu nó tồn tại
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

        // Chèn vào bảng Item (MenuItem)
        db.insert(MENU_ITEM_TABLE, null, values);

        // Đóng kết nối với database
        db.close();
    }


    public void initData() {
        insertEmployee(new Employee(1, "nhanvien1", "123", "Nguyễn Công Phượng", "congphuong@gmail.com", "012356789", "CCCD123"));
        insertEmployee(new Employee(2, "nhanvien2", "123", "Nguyễn Tiến Linh", "tienlinh@gmail.com", "1234556788", "CCCD456"));

        insertCategory(new Category(1, "Thịt nướng"));
        insertCategory(new Category(2, "Tokbokki"));
        insertCategory(new Category(3, "Lẩu"));
        insertCategory(new Category(4, "Ăn vặt"));

        // Sample image paths or URLs
        insertItem(new Product(1, 1, "Ba rọi", "baroi", 100000));
        insertItem(new Product(2, 1, "Ba chỉ bò", "bachibo", 120000));
        insertItem(new Product(3, 1, "Thăng bò", "thangbo", 130000));
        insertItem(new Product(4, 1, "Ba chỉ cuộn nấm", "bachibocuon", 140000));

        // Thêm sản phẩm cho danh mục "C002" - Tokbokki
        insertItem(new Product(5, 2, "Tokbokki truyền thống", "tokbokkitruyenthong", 90000));
        insertItem(new Product(6, 2, "Tokbokki sốt cay", "tokbokkisotcay", 95000));
        insertItem(new Product(7, 2, "Tokbokki sốt phô mai", "tokbokkisotphomai", 85000));
        insertItem(new Product(8, 2, "Tokbokki chiên", "tokbokkichienxu", 100000));

        // Thêm sản phẩm cho danh mục "C003" - Lẩu
        insertItem(new Product(9, 3, "Lẩu bò", "laubo", 200000));
        insertItem(new Product(10, 3, "Lẩu kim chi", "laukimchi", 190000));
        insertItem(new Product(11, 3, "Lẩu nấm", "launam", 210000));
        insertItem(new Product(12, 3, "Lẩu tokbokki", "lautokbokki", 220000));

        // Thêm sản phẩm cho danh mục "C004" - Ăn vặt
        insertItem(new Product(13, 4, "Chả cá hàn quốc", "chacahanquoc", 50000));
        insertItem(new Product(14, 4, "Bánh mì trung quốc", "banhmitrunghanquoc", 45000));
        insertItem(new Product(15, 4, "Manbu", "manbu", 55000));
        insertItem(new Product(16, 4, "Dòi hàn quốc", "doihanquoc", 60000));
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
