package com.example.nhom10.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.Model.BookedTable;
import com.example.nhom10.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookedTablesActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    private RecyclerView recyclerView;
    private BookedTableAdapter adapter;
    private List<BookedTable> bookedTableList;

    private TextView selectedDateTextView, selectedTimeTextView;
    private Button selectDateButton, selectTimeButton, btnBookTable;
    private EditText etCustomerName, etPhone, etEmail;
    private String selectedDate, selectedTime;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    void addControls(){
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_tables);

        addControls();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        addEvents();

        dbHandler = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerViewBookedTables);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookedTableList = new ArrayList<>();
        adapter = new BookedTableAdapter(bookedTableList);
        recyclerView.setAdapter(adapter);
        etCustomerName = findViewById(R.id.etCustomerName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);

        selectDateButton = findViewById(R.id.buttonSelectDate);
        selectTimeButton = findViewById(R.id.buttonSelectTime);
        selectedDateTextView = findViewById(R.id.textViewSelectedDate);
        selectedTimeTextView = findViewById(R.id.textViewSelectedTime);

        btnBookTable = findViewById(R.id.btnBookTable);

        // Khởi tạo giá trị mặc định cho ngày và giờ
        selectedDate = "Chưa chọn ngày";
        selectedTime = "Chưa chọn giờ";

        selectedDateTextView.setText("Ngày đặt: " + selectedDate);
        selectedTimeTextView.setText("Giờ đặt: " + selectedTime);

        // Sự kiện chọn ngày
        selectDateButton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    BookedTablesActivity.this,
                    (view1, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        selectedDateTextView.setText("Ngày đặt: " + selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Sự kiện chọn giờ
        selectTimeButton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    BookedTablesActivity.this,
                    (view12, hourOfDay, minute1) -> {
                        selectedTime = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
                        selectedTimeTextView.setText("Giờ đặt: " + selectedTime);
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        // Sự kiện đặt bàn
        btnBookTable.setOnClickListener(view -> {
            String customerName = etCustomerName.getText().toString();
            String phone = etPhone.getText().toString();
            String email = etEmail.getText().toString();

            // Kiểm tra nếu các trường thông tin đã đầy đủ
            if (customerName.isEmpty() || phone.isEmpty() || email.isEmpty() || selectedDate.equals("Chưa chọn ngày") || selectedTime.equals("Chưa chọn giờ")) {
                Toast.makeText(BookedTablesActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thực hiện lưu đặt bàn vào cơ sở dữ liệu
            dbHandler.insertBooking(customerName, phone, email, selectedDate, selectedTime, 1);
            loadBookedTables();
        });

        // Tải danh sách bàn đã đặt
        loadBookedTables();
    }



    private void loadBookedTables() {
        bookedTableList.clear();
        bookedTableList.addAll(dbHandler.getAllBookedTables()); // Implement a method to fetch all booked tables
        adapter.notifyDataSetChanged();
    }

    void addEvents() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Kiểm tra id của item và khởi động Activity tương ứng
                if (id == R.id.item_info) {
                    startActivity(new Intent(BookedTablesActivity.this, InfoActivity.class));
                } else if (id == R.id.item_statistical) {
                    startActivity(new Intent(BookedTablesActivity.this, revenueActivity.class));
                } else if (id == R.id.item_book) {
                    startActivity(new Intent(BookedTablesActivity.this, BookedTablesActivity.class));
                } else if (id == R.id.item_bill) {
                    startActivity(new Intent(BookedTablesActivity.this, Bill_Activity.class));
                } else if (id == R.id.item_logout) {
                    // Xử lý đăng xuất nếu cần
                } else if (id == R.id.item_dish_management) {
                    startActivity(new Intent(BookedTablesActivity.this, Dish_Management_Activity.class));
                } else if (id == R.id.item_order) {
                    // Start Item_Activity to load the Order fragment
                    Intent intent = new Intent(BookedTablesActivity.this, Item_Activity.class);
                    intent.putExtra("load_order_fragment", true); // Set flag to load order fragment
                    startActivity(intent);
                }
                drawerLayout.closeDrawers(); // Đóng Navigation Drawer sau khi nhấp
                return true;
            }
        });
    }
}
