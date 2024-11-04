package com.example.nhom10.View;

import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PayDetails_Activity extends AppCompatActivity {

    TextView textViewTable, textViewTotalAmount, textViewOrderId, textViewOrderDate, textViewTime;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view
        textViewTable = findViewById(R.id.textViewTable);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        textViewOrderId = findViewById(R.id.textViewOrderId);
        textViewOrderDate = findViewById(R.id.textViewOrderDate);
        textViewTime = findViewById(R.id.textViewOrderTime); // Thêm TextView để hiển thị thời gian
        btnClose = findViewById(R.id.btn_Close);

        // Nhận dữ liệu từ Intent
        int tableId = getIntent().getIntExtra("TABLE_ID", -1);
        double totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0.0);
        String orderId = getIntent().getStringExtra("ORDER_ID"); // Nhận mã đơn hàng từ Intent

        // Hiển thị dữ liệu bàn và tổng tiền
        textViewTable.setText("Bàn: " + tableId);
        textViewTotalAmount.setText("Tổng tiền: " + totalPrice + " VND");

        // Tạo ngày hiện tại
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate.getTime());
        textViewOrderDate.setText("Ngày đặt hàng: " + formattedDate);

        // Hiển thị thời gian
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = timeFormat.format(currentDate.getTime());
        textViewTime.setText("Thời gian: " + formattedTime);

        // Hiển thị mã đơn hàng
        textViewOrderId.setText("Mã đơn hàng: " + orderId);

        // Đóng activity khi bấm nút "Đóng"
        btnClose.setOnClickListener(view -> finish());
    }
}
