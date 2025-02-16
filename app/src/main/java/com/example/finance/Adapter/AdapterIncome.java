package com.example.finance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Activity.Details.DetailsIncomeActivity;
import com.example.finance.Model.Income;
import com.example.finance.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        int imageSource = holder.bind(income);

        Date date = income.getCreatedAt();
        String formattedDate;
        if (date != null) {
            // дату в строку
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            formattedDate = sdf.format(date);
        } else {
            formattedDate = "дата не найдена";
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailsIncomeActivity.class);

                intent.putExtra("image", imageSource);
                intent.putExtra("description", income.getDescription());
                intent.putExtra("price", income.getAmount());
                intent.putExtra("time", formattedDate);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _listIncome.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _imageView;
        public TextView _category;
        public TextView _price;
        private Map<String, Integer> sourceImagesCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._imageView = itemView.findViewById(R.id.itemIncomeImage);
            this._category = itemView.findViewById(R.id.itemIncomeCategory);
            this._price = itemView.findViewById(R.id.itemIncomePrice); // Убедитесь, что ID правильный

            sourceImagesCategory = new HashMap<>();
            sourceImagesCategory.put("Зарплата", R.drawable.salary);
            sourceImagesCategory.put("Подарки", R.drawable.gifts);
            sourceImagesCategory.put("Проценты банка", R.drawable.bank);
            sourceImagesCategory.put("Гос. выплаты", R.drawable.payments);
            sourceImagesCategory.put("Акции", R.drawable.stocks);
            sourceImagesCategory.put("Ценные бумаги", R.drawable.securities);
            sourceImagesCategory.put("Продажа", R.drawable.sell);
            sourceImagesCategory.put("Другое", R.drawable.other);
        }

        public int bind(Income income) {
            Integer imageResource = sourceImagesCategory.get(income.getCategoryId());

            _price.setText(String.valueOf(income.getAmount()));
            _category.setText(income.getCategoryId());

            if (imageResource != null) {
                _imageView.setImageResource(imageResource);
                return imageResource;
            } else {
                _imageView.setImageResource(R.drawable.baseline_access_time_filled_24);
                return R.drawable.baseline_access_time_filled_24;
            }
        }
    }
}

