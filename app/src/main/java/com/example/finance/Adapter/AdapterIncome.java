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

public class AdapterIncome extends RecyclerView.Adapter<AdapterIncome.ViewHolder> {

    private ArrayList<Income> _listIncome;

    public AdapterIncome(ArrayList<Income> listIncome) {
        this._listIncome = listIncome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.item_income, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Income income = _listIncome.get(position);

        holder._category.setText(income.getCategoryId());
        holder._price.setText(String.valueOf(income.getAmount())); // Преобразование в строку
        holder._imageView.setImageResource(R.drawable.baseline_account_circle_24);
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
    }
}

