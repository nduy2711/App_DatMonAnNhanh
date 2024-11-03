package com.example.nhom10.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom10.Model.Bill;
import com.example.nhom10.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private List<Bill> billList;

    public BillAdapter(List<Bill> billList) {
        this.billList = billList;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.textViewBillId.setText(String.valueOf(bill.getBillId()));
        holder.textViewFoodItemName.setText(bill.getFoodItemName());
        holder.textViewTotalAmount.setText(String.valueOf(bill.getTotalAmount()));
        holder.textViewDate.setText(bill.getDate());
        holder.textViewTime.setText(bill.getTime());
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBillId;
        TextView textViewFoodItemName;
        TextView textViewTotalAmount;
        TextView textViewDate;
        TextView textViewTime;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBillId = itemView.findViewById(R.id.textViewBillId);
            textViewFoodItemName = itemView.findViewById(R.id.textViewFoodItemName);
            textViewTotalAmount = itemView.findViewById(R.id.textViewTotalAmount);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}
