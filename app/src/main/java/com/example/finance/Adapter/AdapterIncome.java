package com.example.finance.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Model.Income;
import com.example.finance.Model.User;
import com.example.finance.Model.Waste;
import com.example.finance.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterIncome extends RecyclerView.Adapter<AdapterIncome.ViewHolder> {

    private List<Income> _listIncome;

    public AdapterIncome(List<Income> listIncome) {
        this._listIncome = listIncome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income, parent, false);
        return new AdapterIncome.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Income income = _listIncome.get(position);
        holder.bind(income);
    }

    @Override
    public int getItemCount() {
        return _listIncome.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _imageView;
        public TextView _category;
        public TextView _price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._imageView = itemView.findViewById(R.id.itemIncomeImage);
            this._category = itemView.findViewById(R.id.itemIncomeCategory);
            this._price = itemView.findViewById(R.id.itemIncomePrice); // Убедитесь, что ID правильный
        }

        public void bind(Income income) {
            _price.setText(String.valueOf(income.getAmount()));
            _category.setText(income.getCategoryId());
        }
    }
}

