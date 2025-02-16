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

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText _loginET, _oldPasswordEt, _newPasswordEt;
    private DBUser _dbUser;
    private SPUser _userSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init() {
        _loginET = findViewById(R.id.forgotLoginET);
        _oldPasswordEt = findViewById(R.id.forgotOldPasswordET);
        _newPasswordEt = findViewById(R.id.forgotNewPasswordET);

        _dbUser = new DBUser();
        _userSP = new SPUser(this);
    }

    public void ClickOnForgotUpdatePasswordBtn(View v) {
        String login = _loginET.getText().toString();
        String oldPassword = _oldPasswordEt.getText().toString();
        String newPassword = _newPasswordEt.getText().toString();

        _dbUser.isEmptyUser(login, new DBUser.UserCallback() {
            @Override
            public void onCallback(User user) {
                if (user != null) {
                    // Проверяем, что пароль не равен null перед сравнением
                    if (user.getPassword() != null && user.getPassword().equals(oldPassword)
                            && !(newPassword.isEmpty())) {
                        user.setPassword(newPassword);

                        _userSP.update(user);
                        _dbUser.update(user);

                        Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}