package com.example.nhom10.View;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nhom10.Control.DatabaseHandler;
import com.example.nhom10.Model.Product;
import com.example.nhom10.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
        }

        // Get the product data
        Product product = productList.get(position);

        // Find the views in item_gridview.xml
        ImageView productImage = convertView.findViewById(R.id.item_imageView);
        TextView productName = convertView.findViewById(R.id.item_name);

        // Set product name
        productName.setText(product.getName());

        // Kiểm tra xem đường dẫn hình ảnh có phải là URI không
        if (product.getImage().startsWith("content://") || product.getImage().startsWith("http://") || product.getImage().startsWith("https://")) {
            // Nếu là URI hoặc URL, sử dụng Glide để tải hình ảnh
            Glide.with(context)
                    .load(product.getImage())
                    .placeholder(R.drawable.default_image) // Hình ảnh tạm thời
                    .error(R.drawable.default_image) // Hình ảnh nếu có lỗi
                    .into(productImage);
        } else {
            // Nếu là tên hình ảnh trong drawable, lấy ID và set ảnh
            int imageResId = context.getResources().getIdentifier(product.getImage(), "drawable", context.getPackageName());
            if (imageResId != 0) {
                productImage.setImageResource(imageResId);
            } else {
                productImage.setImageResource(R.drawable.default_image); // Hình ảnh mặc định nếu không tìm thấy
                Log.e("ProductAdapter", "Image resource not found for ID: " + product.getImage());
            }
        }

        return convertView;
    }

    public void updateProducts(ArrayList<Product> newProducts) {
        this.productList.clear();
        this.productList.addAll(newProducts);
        notifyDataSetChanged();
    }
}
