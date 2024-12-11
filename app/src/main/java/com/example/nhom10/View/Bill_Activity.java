package com.example.nhom10.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10.Model.Bill;
import com.example.nhom10.R;
import com.example.nhom10.Control.DatabaseHandler;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Bill_Activity extends AppCompatActivity {
    private Spinner spinnerMonth, spinnerYear;
    private RecyclerView recyclerView;
    private BillAdapter billAdapter;
    private List<Bill> billList = new ArrayList<>();

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    void addControls() {
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerViewInvoices); // Initialize RecyclerView
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        addControls();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        addEvents();

        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        billAdapter = new BillAdapter(billList);
        recyclerView.setAdapter(billAdapter);

        // Load initial data for today
        loadBillsForToday();

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadBillsForSelectedDate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadBillsForSelectedDate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadBillsForToday() {
        // Lấy ngày và năm hiện tại
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng trong Calendar là 0-11
        int year = calendar.get(Calendar.YEAR);

        // Gọi phương thức với tháng và năm
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        billList.clear();
        billList.addAll(dbHandler.getBillsForMonthYear(month, year));
        billAdapter.notifyDataSetChanged();
    }

    private void loadBillsForSelectedDate() {
        String selectedMonth = spinnerMonth.getSelectedItem().toString();
        String selectedYear = spinnerYear.getSelectedItem().toString();
        if (!selectedMonth.equals("Chọn Tháng") && !selectedYear.equals("Chọn Năm")) {
            // Chuyển đổi tháng và năm thành số nguyên
            int month = Integer.parseInt(selectedMonth);
            int year = Integer.parseInt(selectedYear);

            // Gọi phương thức lấy hóa đơn theo tháng và năm
            DatabaseHandler dbHandler = new DatabaseHandler(this);
            billList.clear();
            billList.addAll(dbHandler.getBillsForMonthYear(month, year));
            billAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Vui lòng chọn tháng và năm hợp lệ.", Toast.LENGTH_SHORT).show();
        }
    }

    void addEvents() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Kiểm tra id của item và khởi động Activity tương ứng
                if (id == R.id.item_info) {
                    startActivity(new Intent(Bill_Activity.this, InfoActivity.class));
                } else if (id == R.id.item_statistical) {
                    startActivity(new Intent(Bill_Activity.this, revenueActivity.class));
                } else if (id == R.id.item_book) {
                    startActivity(new Intent(Bill_Activity.this, BookedTablesActivity.class));
                } else if (id == R.id.item_bill) {
                    startActivity(new Intent(Bill_Activity.this, Bill_Activity.class));
                } else if (id == R.id.item_logout) {
                    // Xử lý đăng xuất nếu cần
                } else if (id == R.id.item_dish_management) {
                    startActivity(new Intent(Bill_Activity.this, Dish_Management_Activity.class));
                } else if (id == R.id.item_order) {
                    // Start Item_Activity to load the Order fragment
                    Intent intent = new Intent(Bill_Activity.this, Item_Activity.class);
                    intent.putExtra("load_order_fragment", true); // Set flag to load order fragment
                    startActivity(intent);
                }
                drawerLayout.closeDrawers(); // Đóng Navigation Drawer sau khi nhấp
                return true;
            }
        });

    }

}
