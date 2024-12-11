package com.example.nhom10.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Map;

public class revenueActivity extends AppCompatActivity {
    private Spinner spinnerMonth, spinnerYear;
    private PieChart pieChart;
    private TextView totalRevenue;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    void addControls(){
        pieChart = findViewById(R.id.pieChart);
        totalRevenue = findViewById(R.id.totalRevenue);
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        addControls();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        addEvents();

        // Thiết lập dữ liệu cho Spinner
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        // Lắng nghe sự kiện chọn tháng và năm
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Tải dữ liệu khi người dùng chọn tháng hoặc năm
                loadRevenueData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        spinnerMonth.setOnItemSelectedListener(listener);
        spinnerYear.setOnItemSelectedListener(listener);

        // Tải dữ liệu ban đầu
        loadRevenueData();
    }

    private void loadRevenueData() {
        try {
            // Lấy tháng và năm từ Spinner
            int selectedMonth = Integer.parseInt(spinnerMonth.getSelectedItem().toString());
            int selectedYear = Integer.parseInt(spinnerYear.getSelectedItem().toString());

            // Lấy dữ liệu từ cơ sở dữ liệu
            Map<String, Float> revenueByCategory = getRevenueByCategory(selectedMonth, selectedYear);

            if (revenueByCategory.isEmpty()) {
                // Hiển thị thông báo nếu không có dữ liệu
                totalRevenue.setText("Không có dữ liệu doanh thu.");
                Toast.makeText(this, "Không có dữ liệu để hiển thị.", Toast.LENGTH_LONG).show();
                pieChart.clear(); // Xóa nội dung biểu đồ hiện tại
            } else {
                // Tính tổng doanh thu và cập nhật UI
                float total = calculateTotalRevenue(revenueByCategory);
                totalRevenue.setText("Tổng doanh thu: " + formatCurrency(total) + " VND");

                // Hiển thị biểu đồ
                setupPieChart(revenueByCategory);
            }
        } catch (Exception e) {
            // Xử lý lỗi
            Toast.makeText(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupPieChart(Map<String, Float> revenueByCategory) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : revenueByCategory.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Danh mục");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setCenterText("Doanh thu\nTháng " + spinnerMonth.getSelectedItem().toString());
        pieChart.setCenterTextSize(18f);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(14f);

        pieChart.invalidate();
    }

    private Map<String, Float> getRevenueByCategory(int month, int year) {
        DatabaseHandler dbHelper = new DatabaseHandler(this);
        return dbHelper.getRevenueByCategoryForMonth(month, year); // Truyền tháng và năm vào hàm
    }

    private float calculateTotalRevenue(Map<String, Float> revenueByCategory) {
        float total = 0;
        for (float value : revenueByCategory.values()) {
            total += value;
        }
        return total;
    }
    private String formatCurrency(float amount) {
        return String.format("%,.0f", amount);
    }

    void addEvents() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Kiểm tra id của item và khởi động Activity tương ứng
                if (id == R.id.item_info) {
                    startActivity(new Intent(revenueActivity.this, InfoActivity.class));
                } else if (id == R.id.item_statistical) {
                    startActivity(new Intent(revenueActivity.this, revenueActivity.class));
                } else if (id == R.id.item_book) {
                    startActivity(new Intent(revenueActivity.this, BookedTablesActivity.class));
                } else if (id == R.id.item_bill) {
                    startActivity(new Intent(revenueActivity.this, Bill_Activity.class));
                } else if (id == R.id.item_logout) {
                    // Xử lý đăng xuất nếu cần
                } else if (id == R.id.item_dish_management) {
                    startActivity(new Intent(revenueActivity.this, Dish_Management_Activity.class));
                } else if (id == R.id.item_order) {
                    // Start Item_Activity to load the Order fragment
                    Intent intent = new Intent(revenueActivity.this, Item_Activity.class);
                    intent.putExtra("load_order_fragment", true); // Set flag to load order fragment
                    startActivity(intent);
                }
                drawerLayout.closeDrawers(); // Đóng Navigation Drawer sau khi nhấp
                return true;
            }
        });

    }
}
