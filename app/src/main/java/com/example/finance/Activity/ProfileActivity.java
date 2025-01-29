package com.example.finance.Activity;

import static com.example.finance.R.drawable.baseline_account_circle_24;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.bumptech.glide.Glide;
import com.example.finance.Activity.Login.ForgotPasswordActivity;
import com.example.finance.Activity.Login.LoginActivity;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton _profileImageBtn;
    private ActivityResultLauncher<Intent> _pickImageLauncher;
    private SPUser _spUser;
    private TextView _login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        _pickImageLauncher = registerForActivityResult(//ПОЛУЧЕНИЕ КАРТИНКИ
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data != null && data.getData() != null){
                            Uri imageUri = data.getData();
                            String imageUriString = imageUri.toString();
                            _spUser.saveImageUri(imageUriString);
                            displayImage(imageUri);
                        }
                    }
                }
        );
    }
    private void init(){
        _profileImageBtn = findViewById(R.id.profileImageBtn);
        _spUser = new SPUser(this);

        //Получить и обновить логин
        _login = findViewById(R.id.profileLoginText);
        _login.setText(_spUser.getUserLogin());
    }

    public void clickExitFromAccount(View v){
        try {
            _spUser.delete();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } finally {

        }
    }



    public void clickUpdatePassword(View v){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
    public void clickSetProfileImage(View v){
        openGallery();
    }
    public void clickClearImageProfile(View v){
        clearImage();
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        _pickImageLauncher.launch(intent);
    }

    private void displayImage(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .into(_profileImageBtn);

    }
    private void clearImage() {
        _spUser.removeImageUri();
        _profileImageBtn.setImageResource(baseline_account_circle_24);
        Toast.makeText(this, "Изображение очищено", Toast.LENGTH_SHORT).show();
    }

}