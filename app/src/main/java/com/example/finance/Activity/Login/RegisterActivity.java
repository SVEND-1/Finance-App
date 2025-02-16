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

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText _loginET,_onePasswordET,_twoPasswordET;
    private String _login,_onePassword,_twoPassword;
    private DBUser _userDAO;
    private SPUser _userSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

    }

    private void init(){
        _loginET = findViewById(R.id.registerLoginET);
        _onePasswordET = findViewById(R.id.registerOnePasswordET);
        _twoPasswordET = findViewById(R.id.registerTwoPasswordET);

        _userDAO = new DBUser();
        _userSP = new SPUser(this);
    }

    public void onClickRegisterBtn(View v) {
        _login = _loginET.getText().toString().trim();
        _onePassword = _onePasswordET.getText().toString().trim();
        _twoPassword = _twoPasswordET.getText().toString().trim();


        if (_onePassword.equals(_twoPassword)) {
            if (!_login.isEmpty() && !_onePassword.isEmpty() && !_twoPassword.isEmpty()) {


                _userDAO.getAllUsers(new DBUser.UsersCallback() {//Провекра чтобы человек с таким же логином не было
                    @Override
                    public void onCallback(ArrayList<User> users) {
                        boolean repeatedLogin = false;
                        for(User user : users){
                            if(!user.getLogin().equals(_login)){
                                repeatedLogin = true;
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"Пользователь с таким логином уже есть",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if(repeatedLogin){
                            User user = new User(_login, _onePassword);

                            _userDAO.insert(user);
                            _userSP.insert(user);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });


            } else {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
    }
}