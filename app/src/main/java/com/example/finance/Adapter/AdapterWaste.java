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

public class AdapterWaste extends RecyclerView.Adapter<AdapterWaste.ViewHolder> {

    private ArrayList<Waste> _listWaste;

    public AdapterWaste(ArrayList<Waste> listWaste){
        this._listWaste = listWaste;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.item_waste, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Waste waste = _listWaste.get(position);

        holder._category.setText(waste.getCategoryId());
        holder._price.setText(String.valueOf(waste.getAmount()));
        holder._imageView.setImageResource(R.drawable.baseline_account_circle_24);
    }

    @Override
    public int getItemCount() {
        return _listWaste.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _imageView;
        public TextView _category;
        public TextView _price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._imageView = itemView.findViewById(R.id.itemWasteImage);
            this._category = itemView.findViewById(R.id.itemWasteCategory);
            this._price = itemView.findViewById(R.id.itemWastePrice);
        }
    }
}
