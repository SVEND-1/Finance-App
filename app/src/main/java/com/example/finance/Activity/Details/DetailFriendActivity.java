package com.example.finance.Activity.Details;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finance.Data.DataBase.DBIncome;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Data.DataBase.DBWaste;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Income;
import com.example.finance.Model.User;
import com.example.finance.Model.Waste;
import com.example.finance.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailFriendActivity extends AppCompatActivity {

    private DBUser _dbUser;
    private TextView _login;
    private TextView _wastePerDay,_wastePerMonth,_wastePerYear;
    private TextView _incomePerDay,_incomePerMonth,_incomePerYear;
    private DBIncome _dbIncome;
    private DBWaste _dbWaste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_friend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {//Передать из Friend id и передать пользователя
            String id = bundle.getString("idFriend");
            _dbUser.findUserFromId(id, new DBUser.UserCallback() {
                @Override
                public void onCallback(User user) {

                    _login.setText(user.getLogin());

                    loadWasteData(user.getId(), new DBWaste.DataCallback<List<Integer>>() {
                        @Override
                        public void onSuccess(List<Integer> data) {
                            int day = data.get(0);
                            int month = data.get(1);
                            int year = data.get(2);

                            _wastePerDay.setText(String.valueOf(day));
                            _wastePerMonth.setText(String.valueOf(month));
                            _wastePerYear.setText(String.valueOf(year));
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

                    loadIncomeData(user.getId(), new DBIncome.DataCallback<List<Integer>>() {
                        @Override
                        public void onSuccess(List<Integer> data) {
                            int day = data.get(0);
                            int month = data.get(1);
                            int year = data.get(2);

                            _incomePerDay.setText(String.valueOf(day));
                            _incomePerMonth.setText(String.valueOf(month));
                            _incomePerYear.setText(String.valueOf(year));
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });


                }
            });
        }

    }

    private void init() {
        _dbUser = new DBUser();

        _login = findViewById(R.id.detailFriendLoginText);
        _wastePerDay = findViewById(R.id.detailFriendWastePerDay);
        _wastePerMonth = findViewById(R.id.detailFriendWastePerMonth);
        _wastePerYear = findViewById(R.id.detailFriendWastePerYear);
        _incomePerDay = findViewById(R.id.detailFriendIncomePerDay);
        _incomePerMonth = findViewById(R.id.detailFriendIncomePerMonth);
        _incomePerYear = findViewById(R.id.detailFriendIncomePerYear);

        _dbIncome = new DBIncome();
        _dbWaste = new DBWaste();

    }

    private void loadWasteData(String userId, DBWaste.DataCallback<List<Integer>> callback) {
        _dbWaste.getWasteForUser(userId, new DBWaste.DataCallback<List<Waste>>() {
            @Override
            public void onSuccess(List<Waste> wastes) {
                int day = 0, month = 0, year = 0;
                for (Waste waste : wastes) {
                    Date createdAt = waste.getCreatedAt();
                    if (createdAt == null) continue;

                    Calendar wasteCal = Calendar.getInstance();
                    wasteCal.setTime(createdAt);

                    Calendar todayCal = Calendar.getInstance();

                    wasteCal.set(Calendar.HOUR_OF_DAY, 0);
                    wasteCal.set(Calendar.MINUTE, 0);
                    wasteCal.set(Calendar.SECOND, 0);
                    wasteCal.set(Calendar.MILLISECOND, 0);

                    todayCal.set(Calendar.HOUR_OF_DAY, 0);
                    todayCal.set(Calendar.MINUTE, 0);
                    todayCal.set(Calendar.SECOND, 0);
                    todayCal.set(Calendar.MILLISECOND, 0);

                    if (wasteCal.equals(todayCal)) {
                        day += waste.getAmount();
                    }
                    if (wasteCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH)) {
                        month += waste.getAmount();
                    }
                    if (wasteCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR)) {
                        year += waste.getAmount();
                    }
                }

                List<Integer> result = new ArrayList<>();
                result.add(day);
                result.add(month);
                result.add(year);

                callback.onSuccess(result);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
    private void loadIncomeData(String userId, DBIncome.DataCallback<List<Integer>> callback) {
        _dbIncome.getIncomeForUser(userId, new DBIncome.DataCallback<List<Income>>() {
            @Override
            public void onSuccess(List<Income> incomes) {
                int day = 0, month = 0, year = 0;
                for (Income income : incomes) {
                    Date createdAt = income.getCreatedAt();
                    if (createdAt == null) continue;

                    Calendar wasteCal = Calendar.getInstance();
                    wasteCal.setTime(createdAt);

                    Calendar todayCal = Calendar.getInstance();

                    wasteCal.set(Calendar.HOUR_OF_DAY, 0);
                    wasteCal.set(Calendar.MINUTE, 0);
                    wasteCal.set(Calendar.SECOND, 0);
                    wasteCal.set(Calendar.MILLISECOND, 0);

                    todayCal.set(Calendar.HOUR_OF_DAY, 0);
                    todayCal.set(Calendar.MINUTE, 0);
                    todayCal.set(Calendar.SECOND, 0);
                    todayCal.set(Calendar.MILLISECOND, 0);

                    if (wasteCal.equals(todayCal)) {
                        day += income.getAmount();
                    }
                    if (wasteCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH)) {
                        month += income.getAmount();
                    }
                    if (wasteCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR)) {
                        year += income.getAmount();
                    }
                }

                List<Integer> result = new ArrayList<>();
                result.add(day);
                result.add(month);
                result.add(year);

                callback.onSuccess(result);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}