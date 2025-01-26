package com.example.finance.Data.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.finance.Model.User;


public class SPUser {
    private static final String PREFS_NAME = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    private static final String KYE_BALANCE = "balance";

    private SharedPreferences sharedPreferences;
    public SPUser(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getUserId(){
        return sharedPreferences.getString(KEY_ID,null);
    }
    public String getUserLogin(){
        if (sharedPreferences.contains(KEY_LOGIN)) {
            return sharedPreferences.getString(KEY_LOGIN,null);
        }
        return null;
    }

    public boolean insert(User model) {
        if(model != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_ID, model.getId());
            editor.putString(KEY_LOGIN, model.getLogin());
            editor.putString(KEY_PASSWORD, model.getPassword());
            editor.putInt(KYE_BALANCE, model.getBalance());
            editor.apply();
            return true;
        }
        return false;
    }

    public User read() {
        User user = null;

        user.setId(sharedPreferences.getString(KEY_ID,null));
        user.setLogin(sharedPreferences.getString(KEY_LOGIN,null));
        user.setPassword(sharedPreferences.getString(KEY_PASSWORD,null));
        user.setBalance(sharedPreferences.getInt(KYE_BALANCE,0));

        return user;
    }

    public boolean update(User model) {
        if(model != null){
            insert(model);
            return true;
        }
        return false;
    }

    public boolean delete() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(KEY_ID);
        editor.remove(KEY_LOGIN);
        editor.remove(KEY_PASSWORD);
        editor.remove(KYE_BALANCE);
        editor.apply();
        return false;
    }
}
