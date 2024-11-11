package com.example.nhom10.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

public class Add_dish_Fragment extends DialogFragment {

    private EditText dishNameEditText, dishPriceEditText;
    private Button addDishButton, addImage;
    private ImageView closeButton, selectImage;
    private Spinner categorySpinner;

    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImageUriString;
    private int categoryId;

    public static Add_dish_Fragment newInstance() {
        return new Add_dish_Fragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dish_, container, false);

        // Ánh xạ các view
        dishNameEditText = view.findViewById(R.id.dish_name);
        dishPriceEditText = view.findViewById(R.id.dish_price);
        addDishButton = view.findViewById(R.id.add_dish_button);
        closeButton = view.findViewById(R.id.close_button);
        addImage = view.findViewById(R.id.add_image_button);
        selectImage = view.findViewById(R.id.dish_image_view);
        categorySpinner = view.findViewById(R.id.category_spinner);

        setupSpinner();

        // Sự kiện đóng fragment
        closeButton.setOnClickListener(v -> dismiss());

        // Sự kiện chọn hình ảnh
        addImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Sự kiện thêm món ăn vào cơ sở dữ liệu
        addDishButton.setOnClickListener(v -> addDish());

        return view;
    }

    // Phương thức cài đặt spinner với danh sách category
    private void setupSpinner() {
        String[] categories = {"Meat", "Tokbokki", "Hotpot", "Snack"};
        Integer[] categoryIds = {1, 2, 3, 4};  // ID tương ứng cho mỗi danh mục

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = categoryIds[position]; // Lấy ID tương ứng với danh mục đã chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryId = categoryIds[0]; // Mặc định chọn danh mục đầu tiên
            }
        });
    }

    // Phương thức xử lý kết quả sau khi chọn hình ảnh
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            selectedImageUriString = imageUri.toString();
            selectImage.setImageURI(imageUri);
            Log.d("Add_dish_Fragment", "Selected image URI: " + selectedImageUriString);
        }
    }

    // Phương thức thêm món ăn vào cơ sở dữ liệu
    private void addDish() {
        String dishName = dishNameEditText.getText().toString();
        int dishPrice;

        // Xử lý giá không hợp lệ
        try {
            dishPrice = Integer.parseInt(dishPriceEditText.getText().toString());
        } catch (NumberFormatException e) {
            dishPrice = 0;
        }

        // Kiểm tra xem đã chọn ảnh chưa
        if (selectedImageUriString == null) {
            Toast.makeText(getContext(), "Vui lòng chọn một hình ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        String image = selectedImageUriString;
        DatabaseHandler dbHelper = new DatabaseHandler(getContext());
        int newMenuItemID = dbHelper.getMaxMenuItemID() + 1;

        dbHelper.insertItem(new Product(newMenuItemID, categoryId, dishName, image, dishPrice));
        Toast.makeText(getContext(), "Món ăn đã được thêm thành công", Toast.LENGTH_SHORT).show();

        // Reload fragment cha sau khi thêm món ăn
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof Meat_Fragment) {
            ((Meat_Fragment) parentFragment).loadFragment();
        } else if (parentFragment instanceof Tokbokki_Fragment) {
            ((Tokbokki_Fragment) parentFragment).loadFragment();
        } else if (parentFragment instanceof Hotpot_Fragment) {
            ((Hotpot_Fragment) parentFragment).loadFragment();
        } else if (parentFragment instanceof Snack_Fragment) {
            ((Snack_Fragment) parentFragment).loadFragment();
        }

        dismiss();
    }
}
