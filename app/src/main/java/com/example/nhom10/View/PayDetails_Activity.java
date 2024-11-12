package com.example.nhom10.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom10.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PayDetails_Activity extends AppCompatActivity {

    TextView textViewTotalAmount, textViewOrderId, textViewOrderDate, textViewTime, textViewDishName;
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
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        textViewOrderId = findViewById(R.id.textViewOrderId);
        textViewOrderDate = findViewById(R.id.textViewOrderDate);
        textViewTime = findViewById(R.id.textViewOrderTime);
        textViewDishName = findViewById(R.id.textViewDishName); // Ánh xạ TextView tên món
        btnClose = findViewById(R.id.btn_Close);

        // Nhận dữ liệu từ Intent
        double totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0.0);
        String orderId = getIntent().getStringExtra("ORDER_ID");
        String dishNames = getIntent().getStringExtra("DISH_NAME"); // Nhận tên món từ Intent

        // Hiển thị dữ liệu tổng tiền
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

        // Hiển thị tên món
        textViewDishName.setText("Tên món: " + dishNames); // Hiển thị tên món

        // Đóng activity khi bấm nút "Đóng"
        btnClose.setOnClickListener(view -> finish());
    }
}
