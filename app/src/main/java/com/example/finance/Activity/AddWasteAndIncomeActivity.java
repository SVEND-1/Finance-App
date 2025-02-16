package com.example.finance.Activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.finance.Data.DataBase.DBIncome;
import com.example.finance.Data.DataBase.DBWaste;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Fragment.AddCategoryIncomeFragment;
import com.example.finance.Fragment.AddCategoryWasteFragment;
import com.example.finance.Model.Income;
import com.example.finance.Model.Waste;
import com.example.finance.Other.DataPickerFragment;
import com.example.finance.R;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddWasteAndIncomeActivity extends AppCompatActivity implements DataPickerFragment.DatePickerListener {

    private Fragment fragment = null;
    private LinearLayout _lastClickedLinearLayout = null;
    private TabLayout _tabLayot;
    private String _wasteOrIncome, _categoryName = "";
    private AutoCompleteTextView _description;
    private EditText _sumET, _timeET;
    private DBWaste _dbWaste;
    private DBIncome _dbIncome;
    private SPUser _userSP;
    private Date date;

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
        getIntentFromMain();
    }

    private void init() {
        _description = findViewById(R.id.addWasteAndIncomeDescriptionET);
        _tabLayot = findViewById(R.id.addWasteAndIncomeTabLayoutIncomeOrWaste);
        _sumET = findViewById(R.id.addWasteAndIncomeAmoutET);
        _timeET = findViewById(R.id.addWasteAndIncomeTimeET);

        _dbIncome = new DBIncome();
        _dbWaste = new DBWaste();

        _userSP = new SPUser(this);
    }

    private void getIntentFromMain() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            _wasteOrIncome = bundle.getString("WasteOrIncome");
            if ("Расходы".equals(_wasteOrIncome)) {
                fragment = new AddCategoryWasteFragment();
            } else if ("Доходы".equals(_wasteOrIncome)) {
                fragment = new AddCategoryIncomeFragment();

                TabLayout.Tab defaultTab = _tabLayot.getTabAt(1); //Если выбрал доходы то и верхнее меню будет на доходах
                if (defaultTab != null) {
                    defaultTab.select();
                }
            }

            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.addWasteAndIncomeFrameLayout, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        }
    }


    @Override
    public void onDatePicked(Date date) {
        this.date = date;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(date);

        _timeET.setText(formattedDate);
    }

    public void ClickOnTimeBtn(View v) {
        DataPickerFragment dataPickerFragment = new DataPickerFragment();
        dataPickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }


    public void ClickAddWasteAndIncomeSaveBtn(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        if ("Расходы".equals(_wasteOrIncome)) {
            boolean check = addWasteToDataBase();
            if (check) {
                startActivity(intent);
            }
        } else if ("Доходы".equals(_wasteOrIncome)) {
            boolean check = addIncomeToDataBase();
            if (check) {
                startActivity(intent);
            }
        }
    }

    private boolean addWasteToDataBase() {
        if (!_sumET.getText().toString().isEmpty() && !_description.getText().toString().isEmpty() && !_categoryName.isEmpty() && date != null) {
            int amout = Integer.parseInt(_sumET.getText().toString());
            String userId = _userSP.getUserId();
            String description = _description.getText().toString();

            Waste waste = new Waste(amout, userId, _categoryName, description, date);
            _dbWaste.insert(waste);
            return true;
        } else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean addIncomeToDataBase() {
        if (!_sumET.getText().toString().isEmpty() && !_description.getText().toString().isEmpty() && !_categoryName.isEmpty() && date != null) {
            int amout = Integer.parseInt(_sumET.getText().toString());
            String description = _description.getText().toString();
            String userId = _userSP.getUserId();

            Income income = new Income(amout, userId, _categoryName, description, date);
            _dbIncome.insert(income);
            return true;
        } else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void ClickGetCategory(View v) {
        if (v instanceof LinearLayout) {
            handleLinearLayoutClick((LinearLayout) v);
        }
    }

    private void ClickTableLayout() {
        _tabLayot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                _wasteOrIncome = (String) tab.getText();
                if ("Расходы".equals(_wasteOrIncome)) {
                    fragment = new AddCategoryWasteFragment();
                } else if ("Доходы".equals(_wasteOrIncome)) {
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

    private void handleLinearLayoutClick(LinearLayout linearLayout) {//Это когда нажимаешь на выбор катерогии
        // Сначала сбрасываем цвет предыдущего нажатого LinearLayout, если он существует
        if (_lastClickedLinearLayout != null && _lastClickedLinearLayout != linearLayout) {
            _lastClickedLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        // Устанавливаем цвет для текущего нажатого LinearLayout
        linearLayout.setBackgroundColor(getResources().getColor(R.color.green)); // Устанавливаем цвет в зеленый

        // Обновляем ссылку на последний нажатый LinearLayout
        _lastClickedLinearLayout = linearLayout;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence tooltipText = linearLayout.getTooltipText();
            if (tooltipText != null) {
                _categoryName = tooltipText.toString();
            }
        }
    }

}