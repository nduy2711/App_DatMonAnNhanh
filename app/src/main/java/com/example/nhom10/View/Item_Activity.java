package com.example.nhom10.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nhom10.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Item_Activity extends AppCompatActivity {

    private static final String DB_NAME = "qlbh";
    private static final int DB_VERSION = 1;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    FrameLayout frameLayout;
    BottomNavigationView bt_navigation;
    int tableId; // Khai báo biến tableId


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Check if we need to load the Order fragment
        boolean loadOrderFragment = getIntent().getBooleanExtra("load_order_fragment", false);
        loadFragment(new Meat_Fragment(), true); // Default fragment

        addControls();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        addEvents();

    }

    void addControls() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        bt_navigation = (BottomNavigationView) findViewById(R.id.bt_navigation);
    }

    void addEvents() {
        bt_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.item_meat) {
                    loadFragment(new Meat_Fragment(), false);
                } else if (itemId == R.id.item_tokkboki) {
                    loadFragment(new Tokbokki_Fragment(), false);
                } else if (itemId == R.id.item_hotpot) {
                    loadFragment(new Hotpot_Fragment(), false);
                } else {
                    loadFragment(new Snack_Fragment(), false);
                }
                return true;
            }
        });

        loadFragment(new Meat_Fragment(), true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Kiểm tra id của item và khởi động Activity tương ứng
                if (id == R.id.item_info) {
                    startActivity(new Intent(Item_Activity.this, InfoActivity.class));
                } else if (id == R.id.item_statistical) {
                    startActivity(new Intent(Item_Activity.this, revenueActivity.class));
                } else if (id == R.id.item_book) {
                    startActivity(new Intent(Item_Activity.this, BookedTablesActivity.class));
                } else if (id == R.id.item_bill) {
                    startActivity(new Intent(Item_Activity.this, Bill_Activity.class));
                } else if (id == R.id.item_logout) {
                    startActivity(new Intent(Item_Activity.this, Login_Activity.class));
                } else if (id == R.id.item_dish_management) {
                    startActivity(new Intent(Item_Activity.this, Dish_Management_Activity.class));
                } else if (id == R.id.item_order) {
                    // Start Item_Activity to load the Order fragment
                    Intent intent = new Intent(Item_Activity.this, Item_Activity.class);
                    intent.putExtra("load_order_fragment", true); // Set flag to load order fragment
                    startActivity(intent);
                }

                drawerLayout.closeDrawers(); // Đóng Navigation Drawer sau khi nhấp
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        // Tạo Bundle và truyền tableId vào Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();
    }

}