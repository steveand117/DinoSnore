package com.example.sleeptight;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import android.text.format.DateFormat;

public class TimePickFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener)getActivity(),hours
                ,minutes, DateFormat.is24HourFormat(getActivity()));
    }
}
