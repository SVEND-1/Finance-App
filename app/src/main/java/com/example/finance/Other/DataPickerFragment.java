package com.example.finance.Other;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DataPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DatePickerListener) {
            listener = (DatePickerListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DatePickerListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Установка даты в объект Date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date selectedDate = calendar.getTime();

        // Передача выбранной даты через интерфейс
        if (listener != null) {
            listener.onDatePicked(selectedDate);
        }
    }

    public interface DatePickerListener {
        void onDatePicked(Date date);
    }
}