package com.example.nhom10.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

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
import androidx.viewpager.widget.ViewPager;

import com.example.nhom10.Control.ProductHandler;
import com.example.nhom10.R;
import com.google.android.material.navigation.NavigationView;

public class Main_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    FrameLayout frameLayout;
    ViewPager viewPager;
    RecyclerView recyclerView1, recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        addControls();
        addEvents();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ViewPager viewPager = findViewById(R.id.viewPager);
        int[] imageIds = {R.drawable.tokbokkitruyenthong, R.drawable.chacahanquoc, R.drawable.bachibocuon};  // Thay các hình ảnh của bạn vào đây
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(this, imageIds);
        viewPager.setAdapter(adapter);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        int[] imageIds1 = {R.drawable.bachibocuon, R.drawable.tokbokkichienxu, R.drawable.launam, R.drawable.doihanquoc}; // Hình ảnh cho RecyclerView 1
        String[] imageNames1 = {"Thịt nướng", "Tokbokki", "Lẩu", "Ăn vặt"};
        ImageAdapter adapter1 = new ImageAdapter(this, imageIds1, imageNames1);
        recyclerView1.setAdapter(adapter1);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        int[] imageIds2 = {R.drawable.laubo, R.drawable.laukimchi}; // Hình ảnh cho RecyclerView 2
        String[] imageNames2 = {"Lẩu bò", "Lẩu kim chi"};
        ImageAdapter adapter2 = new ImageAdapter(this, imageIds2, imageNames2);
        recyclerView2.setAdapter(adapter2);

    }

    void addControls() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        viewPager = findViewById(R.id.viewPager);
        recyclerView1 = findViewById(R.id.recyclerCategories);
        recyclerView2 = findViewById(R.id.recyclerProducts);
    }

    void addEvents() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Kiểm tra id của item và khởi động Activity tương ứng
                if (id == R.id.item_info) {
                    startActivity(new Intent(Main_Activity.this, InfoActivity.class));
                } else if (id == R.id.item_statistical) {
                    startActivity(new Intent(Main_Activity.this, revenueActivity.class));
                } else if (id == R.id.item_book) {
                    startActivity(new Intent(Main_Activity.this, BookedTablesActivity.class));
                } else if (id == R.id.item_bill) {
                    startActivity(new Intent(Main_Activity.this, Bill_Activity.class));
                } else if (id == R.id.item_logout) {
                    // Xử lý đăng xuất nếu cần
                } else if (id == R.id.item_dish_management) {
                    startActivity(new Intent(Main_Activity.this, Dish_Management_Activity.class));
                } else if (id == R.id.item_order) {
                    // Start Item_Activity to load the Order fragment
                    Intent intent = new Intent(Main_Activity.this, Item_Activity.class);
                    intent.putExtra("load_order_fragment", true); // Set flag to load order fragment
                    startActivity(intent);
                }

                drawerLayout.closeDrawers(); // Đóng Navigation Drawer sau khi nhấp
                return true;
            }
        });

    }
}