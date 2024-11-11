package com.example.nhom10.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.Model.Bill;
import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Pay_Activty extends AppCompatActivity {

    private ListView productListView;
    private OrderAdapter orderAdapter;
    private TextView totalPriceTextView; // Thêm TextView để hiển thị tổng tiền
    private TextView tvTable; // TextView hiển thị số bàn
    private Button btnCancel, btnPay; // Thêm nút hủy
    private DatabaseHandler databaseHandler; // Khai báo DatabaseHandler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();

        // Khởi tạo DatabaseHandler
        databaseHandler = new DatabaseHandler(this);

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

        // Thêm sự kiện cho nút Pay
        btnPay.setOnClickListener(view -> {
            // Thêm hóa đơn vào cơ sở dữ liệu
            addBillToDatabase(tableId, totalPrice, selectedProducts);
            // Mở activity chi tiết thanh toán
            openPayDetailsActivity(tableId, totalPrice, selectedProducts);
        });
    }

    void addControls() {
        productListView = findViewById(R.id.item_listView);
        totalPriceTextView = findViewById(R.id.total_price);
        btnCancel = findViewById(R.id.btn_cancel); // Khai báo nút hủy
        tvTable = findViewById(R.id.tv_table); // Khai báo TextView hiển thị số bàn
        btnPay = findViewById(R.id.btn_pay);
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

    private void addBillToDatabase(int tableId, double totalPrice, ArrayList<Product> selectedProducts) {
        for (Product product : selectedProducts) {
            // Tạo ID dựa trên thời gian hiện tại
            String idString = generateBillId();

            // Tạo một hóa đơn mới cho mỗi sản phẩm đã chọn với thời gian và ngày thực tế
            Bill bill = new Bill(
                    Long.parseLong(idString), // Chuyển đổi chuỗi thành số nguyên
                    tableId,
                    totalPrice,
                    product.getName(),
                    getCurrentTime(), // Lấy thờsi gian thực tế
                    getCurrentDate()  // Lấy ngày thực tế
            );
            databaseHandler.insertBill(bill); // Thêm hóa đơn vào database
        }
    }

    private void openPayDetailsActivity(int tableId, double totalPrice, ArrayList<Product> selectedProducts) {
        Intent intent = new Intent(this, PayDetails_Activity.class);
        intent.putExtra("TABLE_ID", tableId);
        intent.putExtra("TOTAL_PRICE", totalPrice);

        // Tạo mã đơn hàng để gửi vào Intent
        String orderId = generateBillId(); // Tạo mã đơn hàng
        intent.putExtra("ORDER_ID", orderId); // Gửi mã đơn hàng vào Intent

        // Chuyển đổi danh sách sản phẩm thành tên món
        StringBuilder dishNames = new StringBuilder();
        for (Product product : selectedProducts) {
            if (dishNames.length() > 0) {
                dishNames.append(", "); // Thêm dấu phẩy giữa các tên món
            }
            dishNames.append(product.getName()); // Giả sử Product có phương thức getName() để lấy tên món
        }
        intent.putExtra("DISH_NAME", dishNames.toString()); // Gửi tên món vào Intent

        intent.putParcelableArrayListExtra("SELECTED_PRODUCTS", selectedProducts);
        startActivity(intent);
    }


    private String getCurrentTime() {
        // Lấy thời gian hiện tại theo định dạng "HH:mm"
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(Calendar.getInstance().getTime());
    }

    private String getCurrentDate() {
        // Lấy ngày hiện tại theo định dạng "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private String generateBillId() {
        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // Lấy giờ (0-23)
        int minute = calendar.get(Calendar.MINUTE); // Lấy phút (0-59)
        int second = calendar.get(Calendar.SECOND); // Lấy giây (0-59)
        int day = calendar.get(Calendar.DAY_OF_MONTH); // Lấy ngày (1-31)
        int month = calendar.get(Calendar.MONTH) + 1; // Lấy tháng (0-11, thêm 1)

        // Định dạng ID: HHmmssddMM (không bao gồm năm)
        return String.format("%02d%02d%02d%02d%02d", hour, minute, second, day, month);
    }

}
