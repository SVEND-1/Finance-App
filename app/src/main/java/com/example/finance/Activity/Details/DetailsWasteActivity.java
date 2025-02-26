package com.example.finance.Activity.Details;

import android.os.Bundle;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finance.R;

public class DetailsWasteActivity extends AppCompatActivity {

    private ImageView _imageCategory;
    private TextView _priceText, _timeText;
    private CheckedTextView _descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_waste);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            _priceText.setText(String.valueOf(bundle.getInt("price", 0)));
            _descriptionText.setText(bundle.getString("description", "Описание не найдено"));
            _timeText.setText(bundle.getString("time", "Дата не найдена"));
            _imageCategory.setImageResource(bundle.getInt("image", R.drawable.baseline_access_time_filled_24)); // Исправлено
        }
    }

    private void init() {
        _imageCategory = findViewById(R.id.detailsWasteImageCategory);
        _priceText = findViewById(R.id.detailsWastePriceTV);
        _descriptionText = findViewById(R.id.detailsWasteDescriptionText);
        _timeText = findViewById(R.id.detailsWasteTimeTV);
    }
}