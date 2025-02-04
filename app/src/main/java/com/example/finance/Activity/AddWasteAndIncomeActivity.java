package com.example.finance.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.finance.Data.DAO;
import com.example.finance.Data.DataBase.DBIncome;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Data.DataBase.DBWaste;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Fragment.AddCategoryIncomeFragment;
import com.example.finance.Fragment.AddCategoryWasteFragment;
import com.example.finance.Model.Income;
import com.example.finance.Model.User;
import com.example.finance.Model.Waste;
import com.example.finance.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AddWasteAndIncomeActivity extends AppCompatActivity {

    private Fragment fragment = null;
    private LinearLayout _lastClickedLinearLayout = null;
    private TabLayout _tabLayot;
    private String _wasteOrIncome,_categoryName;
    private AutoCompleteTextView _description;
    private EditText _sumET;
    private DBWaste _wasteDAO;
    private DBIncome _incomeDAO;
    private DBUser _userDAO;
    private SPUser _userSP;
    private List<Waste> _userWastes;

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
    private void init(){
        _description = findViewById(R.id.addWasteAndIncomeDescriptionET);
        _tabLayot = findViewById(R.id.addWasteAndIncomeTabLayoutIncomeOrWaste);
        _sumET = findViewById(R.id.addWasteAndIncomeAmoutET);

        _incomeDAO = new DBIncome();
        _wasteDAO = new DBWaste();
        _userDAO = new DBUser();

        _userSP = new SPUser(this);
    }

    private void getIntentFromMain(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
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

    private void AddWasteToList(Waste waste) {
//        String login = _userSP.getUserLogin();
//        _userDAO.isEmptyUser(login, new DBUser.UserCallback() {
//            @Override
//            public void onCallback(User user) {
//                if (user != null) {
//                    // Получаем список всех Waste объектов (предположим, что у вас есть метод для этого)
//                    List<Waste> allWastes = _wasteDAO.getWasteForUser(user.getId(), new DBWaste.DataCallback<List<Waste>>() {
//                        @Override
//                        public void onSuccess(List<Waste> data) {
//
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//
//                        }
//                    });
//
//                    // Создаем список для хранения отфильтрованных Waste объектов
//                    List<Waste> userWastes = new ArrayList<>();
//
//                    // Фильтруем Waste объекты по userId
//                    for (Waste w : allWastes) {
//                        if (w.getUserId().equals(user.getId())) {
//                            userWastes.add(w);
//                        }
//                    }
//
//                    // Теперь userWastes содержит все Waste объекты, которые относятся к текущему пользователю
//                    // Вы можете использовать этот список по своему усмотрению
//                }
//            }
//        });
    }

//    private void AddIncomeToList(Income income){
//        String login = _userSP.getUserLogin();
//        _userDAO.isEmptyUser (login, new DBUser .UserCallback() {
//            @Override
//            public void onCallback(User user) {
////                if (user != null) {
////                    List<Income> incomeList = user.get_listIncome();
////                    incomeList.add(income);
////                    user.set_listIncome(incomeList);
//                }
//            }
//        });
//    }


    public void ClickAddWasteAndIncomeSaveBtn(View v) {
        if("Расходы".equals(_wasteOrIncome)){
            addWasteToDataBase();
        }
        else if("Доходы".equals(_wasteOrIncome)){
            addIncomeToDataBase();
        }
    }
    private void addWasteToDataBase(){
        int amout = Integer.parseInt(_sumET.getText().toString());
        String userId = _userSP.getUserId();
        String description = _description.getText().toString();

        Waste waste = new Waste(amout,userId,_categoryName,description);
        _wasteDAO.insert(waste);

        AddWasteToList(waste);
    }
    private void addIncomeToDataBase(){
        int amout = Integer.parseInt(_sumET.getText().toString());
        String description = _description.getText().toString();
        String userId = _userSP.getUserId();
        Income income = new Income(amout,userId,_categoryName,description);
        _incomeDAO.insert(income);

//        AddIncomeToList(income);
    }
    public void ClickGetCategory(View v) {
        if (v instanceof LinearLayout) {
            handleLinearLayoutClick((LinearLayout) v);
        }
    }

    private void ClickTableLayout(){
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
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
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