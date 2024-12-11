package com.example.nhom10.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10.Model.BookedTable;
import com.example.nhom10.R;

import java.util.List;

public class BookedTableAdapter extends RecyclerView.Adapter<BookedTableAdapter.ViewHolder> {
    private List<BookedTable> bookedTables;

    public BookedTableAdapter(List<BookedTable> bookedTables) {
        this.bookedTables = bookedTables;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookedTable bookedTable = bookedTables.get(position);
        holder.customerName.setText("Tên: " + bookedTable.getCustomerName());
        holder.phone.setText("Số điện thoại: " + bookedTable.getPhone());
        holder.bookingDate.setText("Ngày đặt: " + bookedTable.getBookingDate());
        holder.bookingTime.setText("Giờ đặt: " + bookedTable.getBookingTime());
    }

    @Override
    public int getItemCount() {
        return bookedTables.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, phone, bookingDate, bookingTime;

        public ViewHolder(View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.tvCustomerName);
            phone = itemView.findViewById(R.id.tvphone);
            bookingDate = itemView.findViewById(R.id.tvBookingDate);
            bookingTime = itemView.findViewById(R.id.tvBookingTime);
        }
    }
}

