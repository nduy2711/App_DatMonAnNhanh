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

import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.Model.Bill;
import com.example.nhom10.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Bill_Activity extends AppCompatActivity {
    private Spinner spinnerMonth, spinnerYear;
    private RecyclerView recyclerView;
    private BillAdapter billAdapter;
    private List<Bill> billList;

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

        // Set up Month Spinner
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.months, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        // Set up Year Spinner
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        loadBills();

        // Handle Month Spinner selection
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMonth = parent.getItemAtPosition(position).toString();
                if (!selectedMonth.equals("Chọn Tháng")) {
                    Toast.makeText(Bill_Activity.this, "Tháng được chọn: " + selectedMonth, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Handle Year Spinner selection
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                if (!selectedYear.equals("Chọn Năm")) {
                    Toast.makeText(Bill_Activity.this, "Năm được chọn: " + selectedYear, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    void loadBills() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        billList = new ArrayList<>();

        // Get all bills from database
        // Assuming you have a method to get all bills in your DatabaseHandler
//        billList = databaseHandler.getAllBills(); // Implement this method in DatabaseHandler
        // Setup RecyclerView
        billAdapter = new BillAdapter(billList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(billAdapter);
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
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
}