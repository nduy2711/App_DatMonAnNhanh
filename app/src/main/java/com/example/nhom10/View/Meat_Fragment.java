package com.example.nhom10.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom10.Control.ProductHandler;
import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Meat_Fragment extends Fragment {

    private GridView gridView;
    private ListView listView;
    private ProductAdapter productAdapter;
    private OrderAdapter orderAdapter;
    private ProductHandler productHandler;
    private TextView totalPriceTextView;
    private Button confirmButton;
    private ImageView addDishButton;

    private ArrayList<Product> selectedProducts = new ArrayList<>();
    private int totalPrice = 0;
    private int tableId;

    public Meat_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meat, container, false);

        // Nhận tableId từ Bundle
        if (getArguments() != null) {
            tableId = getArguments().getInt("TABLE_ID", -1); // Lấy tableId, giá trị mặc định là -1
        }

        // Initialize views
        gridView = view.findViewById(R.id.meat_gridview);
        listView = view.findViewById(R.id.item_listView);
        totalPriceTextView = view.findViewById(R.id.total_price);
        confirmButton = view.findViewById(R.id.confirm_button);
        addDishButton = view.findViewById(R.id.img_addDishButton);

        // Initialize ProductHandler
        productHandler = new ProductHandler(getContext());

        // Get the products for category 1 (Thịt nướng)
        ArrayList<Product> products = productHandler.getProductsByCategory(1);

        // Set up the adapter for GridView
        productAdapter = new ProductAdapter(getContext(), products);
        gridView.setAdapter(productAdapter);

        // Set up the custom adapter for ListView
        orderAdapter = new OrderAdapter(getContext(), selectedProducts);
        listView.setAdapter(orderAdapter);

        // GridView item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product selectedProduct = products.get(i);
                addToOrder(selectedProduct);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Product productToRemove = products.get(position);
                deleteProductAndUpdateUI(productToRemove);
                return true; // Return true to indicate the event was handled
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Product selectedProduct = selectedProducts.get(position);
                removeFromOrder(selectedProduct, position);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });

        return view;
    }

    private void addToOrder(Product product) {
        // Add the selected product to the order list
        selectedProducts.add(product);

        // Update total price
        totalPrice += product.getPrice();

        // Refresh the order ListView and total price
        orderAdapter.notifyDataSetChanged();
        totalPriceTextView.setText("Tổng cộng: " + totalPrice + " VND");
    }

    private void removeFromOrder(Product product, int position) {
        // Remove the selected product from the order list
        selectedProducts.remove(position);

        // Update total price
        totalPrice -= product.getPrice();

        // Refresh the order ListView and total price
        orderAdapter.notifyDataSetChanged();
        totalPriceTextView.setText("Tổng cộng: " + totalPrice + " VND");
    }


    public void loadFragment() {
        ArrayList<Product> updatedMeatProducts = productHandler.getProductsByCategory(1);
        productAdapter.updateProducts(updatedMeatProducts);
        gridView.setAdapter(productAdapter); // Thêm dòng này để làm mới GridView
    }

    private void deleteProductAndUpdateUI(Product product) {
        // Tạo một AlertDialog để xác nhận xóa
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa món ăn " + product.getName() + " này ra khỏi thực đơn không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Nếu người dùng chọn "Có", xóa sản phẩm
                    productHandler.deleteProduct(product.getMenuItemId());
                    Log.d("MeatFragment", "Deleted product with ID: " + product.getMenuItemId());
                    ArrayList<Product> updatedProducts = productHandler.getProductsByCategory(1); // Thay đổi ID danh mục theo từng Fragment
                    productAdapter.updateProducts(updatedProducts);
                    Toast.makeText(getContext(), product.getName() + " đã bị xóa!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Không", null) // Nếu người dùng chọn "Không", chỉ cần đóng dialog
                .show();
    }

    private void confirmOrder() {
        if (selectedProducts.isEmpty()) {
            Toast.makeText(getContext(), "Không có món nào trong giỏ hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo một Intent để chuyển sang Pay_Activty
        Intent intent = new Intent(getActivity(), Pay_Activty.class);
        intent.putParcelableArrayListExtra("selectedProducts", selectedProducts); // Truyền danh sách sản phẩm
        intent.putExtra("totalPrice", totalPrice); // Truyền tổng giá tiền
        intent.putExtra("TABLE_ID", tableId); // Truyền tableId
        startActivity(intent);
    }

}
