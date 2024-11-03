package com.example.nhom10.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

import java.util.ArrayList;

public class Bill_Activity extends AppCompatActivity {

    private ListView productListView;
    private OrderAdapter orderAdapter;
    private TextView totalPriceTextView; // Thêm TextView để hiển thị tổng tiền
    private TextView tvTable; // TextView hiển thị số bàn
    private Button btnCancel; // Thêm nút hủy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();

        // Lấy tableId từ Intent
        int tableId = getIntent().getIntExtra("TABLE_ID", -1); // Đặt -1 làm mặc định để dễ kiểm tra lỗi
        if (tableId == -1) {
            Log.d("BillActivity", "Invalid tableId: -1");
        }


        tvTable.setText("Bàn " + tableId);

        ArrayList<Product> selectedProducts = getIntent().getParcelableArrayListExtra("selectedProducts");

        // Ánh xạ ListView và thiết lập Adapter
        orderAdapter = new OrderAdapter(this, selectedProducts);
        productListView.setAdapter(orderAdapter);

        // Tính toán và hiển thị tổng tiền
        double totalPrice = calculateTotalPrice(selectedProducts);
        totalPriceTextView.setText("Tổng cộng: " + totalPrice + " VND");
    }

    void addControls() {
        productListView = findViewById(R.id.item_listView);
        totalPriceTextView = findViewById(R.id.total_price);
        btnCancel = findViewById(R.id.btn_cancel); // Khai báo nút hủy
        tvTable = findViewById(R.id.tv_table); // Khai báo TextView hiển thị số bàn
    }

    void addEvents() {
        // Sự kiện click cho nút hủy
        btnCancel.setOnClickListener(view -> {
            // Đóng activity và quay lại màn hình trước đó
            finish();
        });
    }

    private double calculateTotalPrice(ArrayList<Product> products) {
        double total = 0.0;
        if (products != null) {
            for (Product product : products) {
                total += product.getPrice(); // Tính tổng giá
            }
        }
        return total;
    }
}
