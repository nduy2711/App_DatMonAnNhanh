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
        holder.textBillId.setText("Bill ID: " + bill.getBillId());
        holder.textTotalAmount.setText("Total Amount: " + bill.getTotalAmount() + " VND");
        holder.textDate.setText("Date: " + bill.getDate());
        holder.textTime.setText("Time: " + bill.getTime());
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView textBillId, textTotalAmount, textDate, textTime;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            textBillId = itemView.findViewById(R.id.textBillId);
            textTotalAmount = itemView.findViewById(R.id.textTotalAmount);
            textDate = itemView.findViewById(R.id.textDate);
            textTime = itemView.findViewById(R.id.textTime);
        }
    }
}