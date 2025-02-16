package com.example.finance.Utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.finance.Adapter.AdapterIncome;
import com.example.finance.Data.DataBase.DBIncome;
import com.example.finance.Data.DataBase.DBWaste;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Income;
import com.example.finance.Model.Waste;
import com.example.finance.MyView.CircleChartView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class IncomeUtils {
    private List<Income> _incomeList;
    private DBIncome _dbIncome;
    private Context context;

    public IncomeUtils(List<Income> incomeList, DBIncome dbIncome, Context context) {
        this._incomeList = incomeList;
        this._dbIncome = dbIncome;
        this.context = context;
    }

    public void DrawCircleIncome(CircleChartView circleChartView) {
        List<CircleChartView.Sector> sectors = new ArrayList<>();
        HashMap<String, Float> incomePercentageOfTheColorInCircle = incomeCircle();

        if (incomePercentageOfTheColorInCircle != null) {
            addSectorIfNotNull(sectors, "Зарплата", incomePercentageOfTheColorInCircle.get("Зарплата"),
                    ContextCompat.getColor(context, android.R.color.holo_red_light));
            addSectorIfNotNull(sectors, "Подарки", incomePercentageOfTheColorInCircle.get("Подарки"),
                    ContextCompat.getColor(context, android.R.color.holo_blue_light));
            addSectorIfNotNull(sectors, "Проценты банка", incomePercentageOfTheColorInCircle.get("Проценты банка"),
                    ContextCompat.getColor(context, android.R.color.holo_green_light));
            addSectorIfNotNull(sectors, "Гос. выплаты", incomePercentageOfTheColorInCircle.get("Гос. выплаты"),
                    ContextCompat.getColor(context, android.R.color.holo_orange_light));
            addSectorIfNotNull(sectors, "Акции", incomePercentageOfTheColorInCircle.get("Акции"),
                    ContextCompat.getColor(context, android.R.color.system_on_primary_dark));
            addSectorIfNotNull(sectors, "Ценные бумаги", incomePercentageOfTheColorInCircle.get("Ценные бумаги"),
                    ContextCompat.getColor(context, android.R.color.holo_red_light));
            addSectorIfNotNull(sectors, "Продажа", incomePercentageOfTheColorInCircle.get("Продажа"),
                    ContextCompat.getColor(context, android.R.color.system_on_error_dark));
            addSectorIfNotNull(sectors, "Другое", incomePercentageOfTheColorInCircle.get("Другое"),
                    ContextCompat.getColor(context, android.R.color.holo_purple));

            circleChartView.setSectors(sectors);
            circleChartView.setCenterText(String.valueOf(incomePercentageOfTheColorInCircle.get("Сумма")));
        }
    }

    public void addSectorIfNotNull(List<CircleChartView.Sector> sectors, String key, Float value, int color) {
        if (value != null && value > 0) {
            sectors.add(new CircleChartView.Sector(value, color));
        }
    }



    public HashMap<String, Float> incomeCircle() {
        HashMap<String, Float> percentageOfTheColorInCircle = new HashMap<>();
        float salary = 0, gifts = 0, bank = 0, payments = 0, stocks = 0, securities = 0, sell = 0, other = 0;
        float sum = 0;

        // Подсчет суммы для каждой категории
        for (Income income : _incomeList) {
            if (income != null && income.getAmount() > 0 && income.getCategoryId() != null) {
                sum += income.getAmount();
                switch (income.getCategoryId()) {
                    case "Зарплата":
                        salary += income.getAmount();
                        break;
                    case "Подарки":
                        gifts += income.getAmount();
                        break;
                    case "Проценты банка":
                        bank += income.getAmount();
                        break;
                    case "Гос. выплаты":
                        payments += income.getAmount();
                        break;
                    case "Акции":
                        stocks += income.getAmount();
                        break;
                    case "Ценные бумаги":
                        securities += income.getAmount();
                        break;
                    case "Продажа":
                        sell += income.getAmount();
                        break;
                    case "Другое":
                        other += income.getAmount();
                        break;
                    default:
                }
            }
        }

        // Проверка на деление на ноль
        if (sum > 0) {
            percentageOfTheColorInCircle.put("Зарплата", (salary * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Подарки", (gifts * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Проценты банка", (bank * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Гос. выплаты", (payments * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Акции", (stocks * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Ценные бумаги", (securities * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Продажа", (sell * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Другое", (other * 100.0f) / sum);
        } else {
            // Если сумма 0, присваиваем 0% всем категориям
            percentageOfTheColorInCircle.put("Зарплата", 0f);
            percentageOfTheColorInCircle.put("Подарки", 0f);
            percentageOfTheColorInCircle.put("Проценты банка", 0f);
            percentageOfTheColorInCircle.put("Гос. выплаты", 0f);
            percentageOfTheColorInCircle.put("Акции", 0f);
            percentageOfTheColorInCircle.put("Ценные бумаги", 0f);
            percentageOfTheColorInCircle.put("Продажа", 0f);
            percentageOfTheColorInCircle.put("Другое", 0f);
        }

        // Добавление суммы
        percentageOfTheColorInCircle.put("Сумма", sum);
        return percentageOfTheColorInCircle;
    }

    public void loadIncomeData(CircleChartView circleChartView,AdapterIncome adapterIncome, String period) {
        SPUser spUser = new SPUser(context);
        _incomeList.clear(); // Очищаем список перед загрузкой новых данных
        adapterIncome.notifyDataSetChanged(); // Уведомляем адаптер об изменении данных

        _dbIncome.getIncomeForUser(spUser.getUserId(), new DBIncome.DataCallback<List<Income>>() {
            @Override
            public void onSuccess(List<Income> data) {
                _incomeList.clear();

                for (Income income : data) {
                    Date createdAt = income.getCreatedAt();
                    if (createdAt == null) continue;

                    Calendar incomeCal = Calendar.getInstance();
                    incomeCal.setTime(createdAt);

                    Calendar todayCal = Calendar.getInstance();
                    Calendar monthCal = Calendar.getInstance();

                    incomeCal.set(Calendar.HOUR_OF_DAY, 0);
                    incomeCal.set(Calendar.MINUTE, 0);
                    incomeCal.set(Calendar.SECOND, 0);
                    incomeCal.set(Calendar.MILLISECOND, 0);

                    todayCal.set(Calendar.HOUR_OF_DAY, 0);
                    todayCal.set(Calendar.MINUTE, 0);
                    todayCal.set(Calendar.SECOND, 0);
                    todayCal.set(Calendar.MILLISECOND, 0);

                    if (incomeCal.equals(todayCal) && period.equals("День")) {
                        _incomeList.add(income);
                    }
                    if (incomeCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH) && period.equals("Месяц")) {
                        _incomeList.add(income);
                    }
                    if (incomeCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) && period.equals("Год")) {
                        _incomeList.add(income);
                    }
                }


                adapterIncome.notifyDataSetChanged();
                DrawCircleIncome(circleChartView); // Обновляем круг

            }

            @Override
            public void onError(Exception e) {
                // Обработка ошибки
            }
        });
    }
    public Income[] mergeSort(Income[] incomes, int left, int right) {//Сортировка слияние O(nlogn)
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(incomes, left, mid);
            mergeSort(incomes, mid + 1, right);
            merge(incomes, left, mid, right);
        }
        return incomes;
    }

    private void merge(Income[] incomes, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Income[] leftArray = new Income[n1];
        Income[] rightArray = new Income[n2];
        System.arraycopy(incomes, left, leftArray, 0, n1);
        System.arraycopy(incomes, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].getCreatedAt().before(rightArray[j].getCreatedAt())) {
                incomes[k] = leftArray[i];
                i++;
            } else {
                incomes[k] = rightArray[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            incomes[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < n2) {
            incomes[k] = rightArray[j];
            j++;
            k++;
        }
    }

}
