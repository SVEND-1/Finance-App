package com.example.finance.Activity.Login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finance.Activity.MainActivity;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.User;
import com.example.finance.R;

public class LoginActivity extends AppCompatActivity {
    private EditText _loginET, _passwordET;
    private SPUser _spUser;
    private DBUser _dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();


        String userLogin = _spUser.getUserLogin();
        String userId = _spUser.getUserId();

        // Если логин и ID пользователя не пустые, переходим в MainActivity
        if (userLogin != null && userId != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void init() {
        _loginET = findViewById(R.id.loginLoginET);
        _passwordET = findViewById(R.id.loginPasswordET);

        _spUser = new SPUser(this);
        _dbUser = new DBUser();
    }

    public void ClickOnSinInBtn(View v) {
        String login = _loginET.getText().toString().trim();
        String password = _passwordET.getText().toString();

        _dbUser.isEmptyUser(login, new DBUser.UserCallback() {
            @Override
            public void onCallback(User user) {
                if (user != null) {
                    // Проверяем, что пароль не равен null перед сравнением
                    if (user.getPassword() != null && user.getPassword().equals(password)) {
                        _spUser.insert(user);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ClickOnToRegister(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void ClickOnToForgotPassword(View v) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}