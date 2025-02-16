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

import com.example.finance.Activity.Details.DetailsWasteActivity;
import com.example.finance.Model.Waste;
import com.example.finance.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        int imageSource = holder.bind(waste);

        Date date = waste.getCreatedAt();
        String formattedDate;
        if (date != null) {
            //дату в строку
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            formattedDate = sdf.format(date);
        } else {
            formattedDate = "дата не найдена";
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailsWasteActivity.class);

                intent.putExtra("image", imageSource);
                intent.putExtra("description", waste.getDescription());
                intent.putExtra("price", waste.getAmount());
                intent.putExtra("time", formattedDate);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return wasteList.size();
    }

    public static class WasteViewHolder extends RecyclerView.ViewHolder {
        private ImageView _imageView;
        private TextView _amountTextView;
        private TextView _categoryTextView;
        private Map<String, Integer> sourceImagesCategory;

        public WasteViewHolder(@NonNull View itemView) {
            super(itemView);
            _imageView = itemView.findViewById(R.id.itemWasteImage);
            _amountTextView = itemView.findViewById(R.id.itemWastePrice);
            _categoryTextView = itemView.findViewById(R.id.itemWasteCategory);

            sourceImagesCategory = new HashMap<>();
            sourceImagesCategory.put("Образование", R.drawable.education);
            sourceImagesCategory.put("Продукты", R.drawable.products);
            sourceImagesCategory.put("Одежда", R.drawable.clothes);
            sourceImagesCategory.put("Больница", R.drawable.hospital);
            sourceImagesCategory.put("Спорт", R.drawable.sport);
            sourceImagesCategory.put("Транспорт", R.drawable.transport);
            sourceImagesCategory.put("Досуг", R.drawable.leisure);
            sourceImagesCategory.put("Другое", R.drawable.other);
        }

        public int bind(Waste waste) {
            Integer imageResource = sourceImagesCategory.get(waste.getCategoryId());

            _amountTextView.setText(String.valueOf(waste.getAmount()));
            _categoryTextView.setText(waste.getCategoryId());

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
