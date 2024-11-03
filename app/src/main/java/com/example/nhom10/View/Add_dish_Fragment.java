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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

public class Add_dish_Fragment extends DialogFragment {

    private EditText dishNameEditText, dishPriceEditText;
    private Button addDishButton, addImage;
    private ImageView closeButton, selectImage;

    private static final String ARG_CATEGORY_ID = "category_id";
    private int categoryId;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImageUriString; // Lưu URI của hình ảnh đã chọn

    public static Add_dish_Fragment newInstance(int categoryId) {
        Add_dish_Fragment fragment = new Add_dish_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dish_, container, false);

        dishNameEditText = view.findViewById(R.id.dish_name);
        dishPriceEditText = view.findViewById(R.id.dish_price);
        addDishButton = view.findViewById(R.id.add_dish_button);
        closeButton = view.findViewById(R.id.close_button);
        addImage = view.findViewById(R.id.add_image_button);
        selectImage = view.findViewById(R.id.dish_image_view);

        closeButton.setOnClickListener(v -> dismiss());

        addImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

         addDishButton.setOnClickListener(v -> {
            String dishName = dishNameEditText.getText().toString();
            int dishPrice;

            try {
                dishPrice = Integer.parseInt(dishPriceEditText.getText().toString());
            } catch (NumberFormatException e) {
                dishPrice = 0; // Xử lý giá không hợp lệ
            }

            if (selectedImageUriString == null) {
                Toast.makeText(getContext(), "Vui lòng chọn một hình ảnh", Toast.LENGTH_SHORT).show();
                return;
            }

            String image = selectedImageUriString;

            DatabaseHandler dbHelper = new DatabaseHandler(getContext());
            int newMenuItemID = dbHelper.getMaxMenuItemID() + 1;

             dbHelper.insertItem(new Product(newMenuItemID, categoryId, dishName, image, dishPrice));
            Toast.makeText(getContext(), "Món ăn đã được thêm thành công", Toast.LENGTH_SHORT).show();
             Fragment parentFragment = getParentFragment();
             if (parentFragment instanceof Meat_Fragment) {
                 ((Meat_Fragment) parentFragment).loadFragment(); // Reload MeatFragment
             } else if (parentFragment instanceof Tokbokki_Fragment) {
                 ((Tokbokki_Fragment) parentFragment).loadFragment(); // Reload TokbokkiFragment
             } else if (parentFragment instanceof Hotpot_Fragment) {
                 ((Hotpot_Fragment) parentFragment).loadFragment(); // Reload HotpotFragment
             } else if (parentFragment instanceof Snack_Fragment) {
                 ((Snack_Fragment) parentFragment).loadFragment(); // Reload SnackFragment
             }
            dismiss();
        });

        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            selectedImageUriString = imageUri.toString(); // Lưu URI của hình ảnh đã chọn
            selectImage.setImageURI(imageUri); // Hiển thị hình ảnh đã chọn
            Log.d("Add_dish_Fragment", "Selected image URI: " + selectedImageUriString); // In ra giá trị URI
        }
    }

}
