package com.example.finance.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Data.DataBase.DBFriend;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Friend;
import com.example.finance.Model.User;
import com.example.finance.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdapterMyFriend extends RecyclerView.Adapter<AdapterMyFriend.ViewHolder>{
    private ArrayList<Friend> friends;

    public AdapterMyFriend(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_friend, parent, false);
        return new AdapterMyFriend.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView _imageView;
        public TextView _name;
        public FloatingActionButton _bntDeleteFriend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this._imageView = itemView.findViewById(R.id.itemMyFriendImage);
            this._name = itemView.findViewById(R.id.itemMyFriendName);
            this._bntDeleteFriend = itemView.findViewById(R.id.itemMyFriendFloatingBtnDelete);
        }

        public void bind(Friend friend) {
            DBUser dbUser = new DBUser();

            dbUser.findUserFromId(friend.getFriendUserId(), new DBUser.UserCallback() {
                @Override
                public void onCallback(User user) {
                    _name.setText(user.getLogin());

                    _bntDeleteFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            });


        }
    }
}
