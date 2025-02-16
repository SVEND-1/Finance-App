package com.example.finance.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Data.DataBase.DBFriend;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Friend;
import com.example.finance.Model.User;
import com.example.finance.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdapterAddFriend extends RecyclerView.Adapter<AdapterAddFriend.ViewHolder> {
    private ArrayList<User> _filteredUserList;

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
        return _filteredUserList == null ? 0 : _filteredUserList.size();
    }

    // Метод для обновления списка
    public void searchDataList(ArrayList<User> searchList) {
        _filteredUserList.clear();
        _filteredUserList.addAll(searchList);
        notifyDataSetChanged(); // Уведомляем адаптер об изменении данных
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _imageView;
        public TextView _name;
        public FloatingActionButton _bntAddFriend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._imageView = itemView.findViewById(R.id.itemAddFriendImage);
            this._name = itemView.findViewById(R.id.itemAddFriendName);
            this._bntAddFriend = itemView.findViewById(R.id.itemAddFriendFloatingBtnAdd);
        }

        public void bind(User user) {
            _name.setText(user.getLogin());

            _bntAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBFriend dbFriend = new DBFriend(itemView.getContext());
                    SPUser spUser = new SPUser(itemView.getContext());

                    dbFriend.getAllFriends(new DBFriend.FriendsCallback() {
                        @Override
                        public void onCallback(ArrayList<Friend> friends) {
                            boolean alreadyAdded = false;
                            for (Friend friend : friends) {
                                if (friend.getFriendUserId().equals(user.getId())) {
                                    alreadyAdded = true;
                                    Toast.makeText(itemView.getContext(), "Этот пользователь уже добавлен", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (!alreadyAdded) {
                                Friend newFriend = new Friend(spUser.getUserId(), user.getId());
                                dbFriend.insert(newFriend);
                                Toast.makeText(itemView.getContext(), "Пользователь добавлен", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
}