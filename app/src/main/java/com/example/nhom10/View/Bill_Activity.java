package com.example.nhom10.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
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
    FrameLayout frameLayout;

    void addControls() {
        frameLayout = findViewById(R.id.frameLayout);
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

        // Initialize spinners
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);

        // Set up Month and Year Spinners
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

        // Handle Spinner selections to load data for selected month and year
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
        int day = calendar.get(Calendar.DAY_OF_MONTH);
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
                    startActivity(new Intent(Bill_Activity.this, StatisticalActivity.class));
                } else if (id == R.id.item_book) {
                    startActivity(new Intent(Bill_Activity.this, BookActivity.class));
                } else if (id == R.id.item_bill) {
                    startActivity(new Intent(Bill_Activity.this, Bill_Activity.class));
                } else if (id == R.id.item_logout) {
                    // Thêm mã để xử lý đăng xuất nếu cần
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
