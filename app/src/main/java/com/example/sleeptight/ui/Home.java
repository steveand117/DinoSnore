package com.example.sleeptight.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.sleeptight.R;

public class Home extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private Button setTime;
    private Button awake;
    private TextView showTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_target);
        setTime = findViewById(R.id.target);
        showTime = findViewById(R.id.current_tar_time);
        setTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment dialogFragment = new TimePickFragment();
                dialogFragment.show(getSupportFragmentManager(),"Time Picker");

            }
        });

        awake = findViewById(R.id.check_in);
        awake.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String timestamp = "";
        boolean isam = true;
        if (hourOfDay == 0) {
            timestamp += "12:";
        } else if (hourOfDay == 12) {
            timestamp += "12:";
            isam = false;
        } else if (hourOfDay > 12){
            timestamp += (hourOfDay - 12) + ":";
            isam = false;
        } else {
            timestamp += hourOfDay + ":";
        }
        if (minute < 10) {
           timestamp += "0" + minute;
        } else {
            timestamp += minute;
        }
        if (isam) {
            timestamp += "AM";
        } else {
            timestamp += "PM";
        }

        showTime.setText(timestamp);
    }
}
