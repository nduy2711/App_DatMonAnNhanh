package com.example.nhom10.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.nhom10.Control.ProductHandler;
import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

import java.util.ArrayList;

public class Dish_Management_Activity extends AppCompatActivity {
    private ListView dishListView;
    private DishAdapter dishAdapter;
    private ProductHandler productHandler;
    private Spinner dishTypeSpinner;
    private Button addDishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_management);

        addControls();
        addEvents();
    }

    private void addControls() {
        dishListView = findViewById(R.id.dish_listView);
        dishTypeSpinner = findViewById(R.id.dish_type_spinner);
        addDishButton = findViewById(R.id.add_dish_button);

        productHandler = new ProductHandler(this);

        ArrayList<Product> allDishes = productHandler.getProductsByCategory(2);
        dishAdapter = new DishAdapter(this, allDishes);
        dishListView.setAdapter(dishAdapter);

        String[] dishCategories = {"Meat", "Tokbokki", "Hotpot", "Snack", "All"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dishCategories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dishTypeSpinner.setAdapter(spinnerAdapter);
    }

    // Thiết lập các sự kiện cho các thành phần giao diện
    private void addEvents() {
        dishTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                String selectedType = (String) parentView.getItemAtPosition(position);
                filterDishesByType(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        addDishButton.setOnClickListener(v -> openAddDishDialog());

        // Xử lý sự kiện nhấn đè để xóa sản phẩm trong ListView
        dishListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Product selectedProduct = (Product) dishAdapter.getItem(position);

            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa món " + selectedProduct.getName() + " này không?")
                    .setPositiveButton("Đồng ý", (dialog, which) -> {
                        productHandler.deleteProduct(selectedProduct.getMenuItemId());
                        dishAdapter.remove(selectedProduct);
                        dishAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Đã xóa " + selectedProduct.getName(), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();

            return true;
        });
    }

    private void openAddDishDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Add_dish_Fragment addDishFragment = Add_dish_Fragment.newInstance();
        addDishFragment.show(fragmentManager, "addDishFragment");
    }

    // Filter dishes based on selected type
    private void filterDishesByType(String dishType) {
        ArrayList<Product> filteredDishes;

        switch (dishType) {
            case "Meat":
                filteredDishes = productHandler.getProductsByCategory(1);
                break;
            case "Tokbokki":
                filteredDishes = productHandler.getProductsByCategory(2);
                break;
            case "Hotpot":
                filteredDishes = productHandler.getProductsByCategory(3);
                break;
            case "Snack":
                filteredDishes = productHandler.getProductsByCategory(4);
                break;
            default:
                filteredDishes = productHandler.getProductsByCategory(1);
                break;
        }

        // Update the ListView with filtered dishes
        dishAdapter.updateProducts(filteredDishes);
    }
}
