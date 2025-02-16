package com.example.finance.Data.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.finance.Model.User;


public class SPUser {
    private static final String PREFS_NAME = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    private static final String KYE_IMAGE_USER_URI = "image_user";

    private SharedPreferences sharedPreferences;

    public SPUser(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public void saveImageUri(String imageUri) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KYE_IMAGE_USER_URI, imageUri);
        editor.apply();
    }


    public void removeImageUri() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KYE_IMAGE_USER_URI);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_ID, null);
    }

    public String getUserLogin() {
        if (sharedPreferences.contains(KEY_LOGIN)) {
            return sharedPreferences.getString(KEY_LOGIN, null);
        }
        return null;
    }

    public boolean insert(User model) {
        if (model != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_ID, model.getId());
            editor.putString(KEY_LOGIN, model.getLogin());
            editor.putString(KEY_PASSWORD, model.getPassword());
            editor.apply();
            return true;
        }
        return false;
    }


    public boolean update(User model) {
        if (model != null) {
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
        editor.apply();
        return false;
    }
}
