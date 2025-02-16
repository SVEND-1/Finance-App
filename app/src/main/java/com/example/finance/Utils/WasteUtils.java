package com.example.finance.Utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.finance.Adapter.AdapterWaste;
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

public class WasteUtils {
    private List<Waste> _wasteList;
    private DBWaste _dbWaste;
    private Context context;

    public WasteUtils(List<Waste> _wasteList, DBWaste _dbWaste, Context context) {
        this._wasteList = _wasteList;
        this._dbWaste = _dbWaste;
        this.context = context;
    }

    public void DrawCircleWaste(CircleChartView circleChartView) {
        List<CircleChartView.Sector> sectors = new ArrayList<>();
        HashMap<String, Float> wastePercentageOfTheColorInCircle = wasteCircle();

        if (wastePercentageOfTheColorInCircle != null) {
            addSectorIfNotNull(sectors, "Образование", wastePercentageOfTheColorInCircle.get("Образование"),
                    ContextCompat.getColor(context, android.R.color.holo_red_light));
            addSectorIfNotNull(sectors, "Продукты", wastePercentageOfTheColorInCircle.get("Продукты"),
                    ContextCompat.getColor(context, android.R.color.holo_blue_light));
            addSectorIfNotNull(sectors, "Одежда", wastePercentageOfTheColorInCircle.get("Одежда"),
                    ContextCompat.getColor(context, android.R.color.holo_green_light));
            addSectorIfNotNull(sectors, "Больница", wastePercentageOfTheColorInCircle.get("Больница"),
                    ContextCompat.getColor(context, android.R.color.holo_orange_light));
            addSectorIfNotNull(sectors, "Спорт", wastePercentageOfTheColorInCircle.get("Спорт"),
                    ContextCompat.getColor(context, android.R.color.black));
            addSectorIfNotNull(sectors, "Транспорт", wastePercentageOfTheColorInCircle.get("Транспорт"),
                    ContextCompat.getColor(context, android.R.color.darker_gray));
            addSectorIfNotNull(sectors, "Досуг", wastePercentageOfTheColorInCircle.get("Досуг"),
                    ContextCompat.getColor(context, android.R.color.system_on_error_dark));
            addSectorIfNotNull(sectors, "Другое", wastePercentageOfTheColorInCircle.get("Другое"),
                    ContextCompat.getColor(context, android.R.color.system_accent1_0));

            circleChartView.setSectors(sectors);
            circleChartView.setCenterText(String.valueOf(wastePercentageOfTheColorInCircle.get("Сумма")));
        }
    }

    private void addSectorIfNotNull(List<CircleChartView.Sector> sectors, String key, Float value, int color) {
        if (value != null && value > 0) {
            sectors.add(new CircleChartView.Sector(value, color));
        }
    }

    public HashMap<String, Float> wasteCircle() {

        HashMap<String, Float> percentageOfTheColorInCircle = new HashMap<>();
        float education = 0, product = 0, clothes = 0, hospital = 0, sport = 0, transport = 0, leisure = 0, other = 0;
        float sum = 0;

        for (Waste waste : _wasteList) {
            if (waste != null && waste.getAmount() > 0 && waste.getCategoryId() != null) {
                sum += waste.getAmount();
                switch (waste.getCategoryId()) {
                    case "Образование":
                        education += waste.getAmount();
                        break;
                    case "Продукты":
                        product += waste.getAmount();
                        break;
                    case "Одежда":
                        clothes += waste.getAmount();
                        break;
                    case "Больница":
                        hospital += waste.getAmount();
                        break;
                    case "Спорт":
                        sport += waste.getAmount();
                        break;
                    case "Транспорт":
                        transport += waste.getAmount();
                        break;
                    case "Досуг":
                        leisure += waste.getAmount();
                        break;
                    case "Другое":
                        other += waste.getAmount();
                        break;
                }
            }
        }

        if (sum > 0) {
            percentageOfTheColorInCircle.put("Образование", (education * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Продукты", (product * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Одежда", (clothes * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Больница", (hospital * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Спорт", (sport * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Транспорт", (transport * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Досуг", (leisure * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Другое", (other * 100.0f) / sum);
        } else {
            percentageOfTheColorInCircle.put("Образование", 0f);
            percentageOfTheColorInCircle.put("Продукты", 0f);
            percentageOfTheColorInCircle.put("Одежда", 0f);
            percentageOfTheColorInCircle.put("Больница", 0f);
            percentageOfTheColorInCircle.put("Спорт", 0f);
            percentageOfTheColorInCircle.put("Транспорт", 0f);
            percentageOfTheColorInCircle.put("Досуг", 0f);
            percentageOfTheColorInCircle.put("Другое", 0f);
        }
        percentageOfTheColorInCircle.put("Сумма",sum);
        return percentageOfTheColorInCircle;
    }

    public void loadWasteData(AdapterWaste adapterWaste,String period) {
        SPUser userSP = new SPUser(context);
        _dbWaste.getWasteForUser(userSP.getUserId(), new DBWaste.DataCallback<List<Waste>>() {
            @Override
            public void onSuccess(List<Waste> wastes) {
                _wasteList.clear();
                for(Waste waste : wastes){
                    Date createdAt = waste.get_createdAt();
                    if (createdAt == null) continue; // Проверка на null


                    Calendar wasteCal = Calendar.getInstance();
                    wasteCal.setTime(createdAt);

                    Calendar todayCal = Calendar.getInstance();
                    Calendar monthCal = Calendar.getInstance();


                    // Обнуляем время чтобы сравнить именно за день
                    wasteCal.set(Calendar.HOUR_OF_DAY, 0);
                    wasteCal.set(Calendar.MINUTE, 0);
                    wasteCal.set(Calendar.SECOND, 0);
                    wasteCal.set(Calendar.MILLISECOND, 0);

                    todayCal.set(Calendar.HOUR_OF_DAY, 0);
                    todayCal.set(Calendar.MINUTE, 0);
                    todayCal.set(Calendar.SECOND, 0);
                    todayCal.set(Calendar.MILLISECOND, 0);


                    if (wasteCal.equals(todayCal) && period.equals("День")) {
                        _wasteList.add(waste);
                    }
                    if(wasteCal.getTime().getMonth() == todayCal.getTime().getMonth() && period.equals("Месяц")){
                        _wasteList.add(waste);
                    }
                    if(wasteCal.getTime().getYear() == todayCal.getTime().getYear() && period.equals("Год")){
                        _wasteList.add(waste);
                    }
                }
                adapterWaste.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public Waste[] mergeSort(Waste[] wastes, int left, int right) {//Сортировка слияние O(nlogn)
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(wastes, left, mid);
            mergeSort(wastes, mid + 1, right);
            merge(wastes, left, mid, right);
        }
        return wastes;
    }

    private void merge(Waste[] wastes, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Waste[] leftArray = new Waste[n1];
        Waste[] rightArray = new Waste[n2];
        System.arraycopy(wastes, left, leftArray, 0, n1);
        System.arraycopy(wastes, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].get_createdAt().before(rightArray[j].get_createdAt())) {
                wastes[k] = leftArray[i];
                i++;
            } else {
                wastes[k] = rightArray[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            wastes[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < n2) {
            wastes[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
