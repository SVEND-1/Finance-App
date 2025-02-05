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

import com.example.finance.Model.User;
import com.example.finance.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdapterAddFriend extends RecyclerView.Adapter<AdapterAddFriend.ViewHolder> {


    private ArrayList<User> _filteredUserList; // Отфильтрованный список

    public AdapterAddFriend(ArrayList<User> userList) {
        this._filteredUserList = new ArrayList<>(userList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = _filteredUserList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return _filteredUserList.size();
    }

    public void searchDataList(ArrayList<User> searchList) {
        _filteredUserList.clear();
        _filteredUserList.addAll(searchList);
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

        public void bind(User user) {
            _name.setText(user.getLogin());
            _bntAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Логика добавления друга
                }
            });
        }
    }
}