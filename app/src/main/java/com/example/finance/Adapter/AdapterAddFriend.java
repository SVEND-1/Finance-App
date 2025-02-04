package com.example.finance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finance.Model.Income;
import com.example.finance.Model.User;
import com.example.finance.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdapterAddFriend extends RecyclerView.Adapter<AdapterAddFriend.ViewHolder> {

    private Context _context;
    private ArrayList<User> _userList;

    public AdapterAddFriend(Context context, ArrayList<User> userList) {
        this._context = context;
        this._userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.item_add_friend, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = _userList.get(position);
        holder._name.setText(user.getLogin());

        // Используйте Glide для загрузки изображения
        Glide.with(_context)
                .load(R.drawable.baseline_account_circle_24)
                .placeholder(R.drawable.baseline_account_circle_24)
                .into(holder._imageView);

        holder._bntAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Логика добавления друга

            }
        });
    }

    @Override
    public int getItemCount() {
        return _userList.size();
    }

    public void searchDataList(ArrayList<User> searchList) {
        _userList.clear();
        _userList.addAll(searchList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _imageView;
        public TextView _name;
        public FloatingActionButton _bntAddFriend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._imageView = itemView.findViewById(R.id.itemAddFriendImage);
            this._name = itemView.findViewById(R.id.itemIncomeCategory);
            this._bntAddFriend = itemView.findViewById(R.id.itemAddFriendFloatingBtnAdd);
        }
    }
}