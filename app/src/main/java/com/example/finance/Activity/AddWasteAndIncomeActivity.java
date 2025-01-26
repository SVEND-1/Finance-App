package com.example.finance.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.finance.Fragment.AddCategoryIncomeFragment;
import com.example.finance.Fragment.AddCategoryWasteFragment;
import com.example.finance.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class AddWasteAndIncomeActivity extends AppCompatActivity {

    private TextView _textView;
    private Fragment fragment = null;
    private LinearLayout _lastClickedLinearLayout = null;
    private TabLayout _tabLayot;

    private AutoCompleteTextView _description;

    private TabItem _tabItemWaste,_tabItemIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_waste_and_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        ClickTableLayout();
    }
    private void init(){
        _textView = findViewById(R.id.addWasteAndIncomeSumText);
        _description = findViewById(R.id.addWasteAndIncomeDescriptionET);
        _tabLayot = findViewById(R.id.addWasteAndIncomeTabLayoutIncomeOrWaste);
        _tabItemWaste = findViewById(R.id.addWasteAndIncomeTabWaste);
        _tabItemIncome = findViewById(R.id.addWasteAndIncomeTabIncome);
    }


    public void ClickGetCategory(View v) {
        if (v instanceof CardView) {
            handleCardViewClick((CardView) v);
        } else if (v instanceof LinearLayout) {
            handleLinearLayoutClick((LinearLayout) v);
        }
    }

    private void ClickTableLayout(){
        _tabLayot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = (String) tab.getText();
                if ("Расходы".equals(tabText)) {
                    fragment = new AddCategoryWasteFragment();
                } else if ("Доходы".equals(tabText)) {
                    fragment = new AddCategoryIncomeFragment();
                }


                if (fragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.addWasteAndIncomeFrameLayout, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void handleCardViewClick(CardView cardView) {
        String categoryText = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence tooltipText = cardView.getTooltipText();
            if (tooltipText != null) {
                categoryText = tooltipText.toString();
            }
        }
        _textView.setText(categoryText);
    }

    private void handleLinearLayoutClick(LinearLayout linearLayout) {//Это когда нажимаешь на выбор катерогии
        // Сначала сбрасываем цвет предыдущего нажатого LinearLayout, если он существует
        if (_lastClickedLinearLayout != null && _lastClickedLinearLayout != linearLayout) {
            _lastClickedLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        // Устанавливаем цвет для текущего нажатого LinearLayout
        linearLayout.setBackgroundColor(getResources().getColor(R.color.green)); // Устанавливаем цвет в зеленый

        // Обновляем ссылку на последний нажатый LinearLayout
        _lastClickedLinearLayout = linearLayout;
    }

}