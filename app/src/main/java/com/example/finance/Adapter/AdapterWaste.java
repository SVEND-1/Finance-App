package com.example.finance.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Model.Income;
import com.example.finance.Model.Waste;
import com.example.finance.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterWaste extends RecyclerView.Adapter<AdapterWaste.WasteViewHolder> {

    private List<Waste> wasteList;

    public AdapterWaste(List<Waste> wasteList) {
        this.wasteList = wasteList;
    }

    @NonNull
    @Override
    public WasteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_waste, parent, false);
        return new WasteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WasteViewHolder holder, int position) {
        Waste waste = wasteList.get(position);
        holder.bind(waste);
    }

    @Override
    public int getItemCount() {
        return wasteList.size();
    }

    public static class WasteViewHolder extends RecyclerView.ViewHolder {

        private TextView _amountTextView;
        private TextView _categoryTextView;

        public WasteViewHolder(@NonNull View itemView) {
            super(itemView);
            _amountTextView = itemView.findViewById(R.id.itemWastePrice);
            _categoryTextView = itemView.findViewById(R.id.itemWasteCategory);
        }

        public void bind(Waste waste) {
            _amountTextView.setText(String.valueOf(waste.getAmount()));
            _categoryTextView.setText(waste.getCategoryId());
        }
    }
}
