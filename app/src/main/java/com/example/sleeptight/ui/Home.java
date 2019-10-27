package com.example.sleeptight.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.sleeptight.MainActivity;
import com.example.sleeptight.R;
import com.example.sleeptight.Stats;
import com.example.sleeptight.User;

import java.util.Calendar;

public class Home extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private Button setTime;
    private Button awake;
    private Button dataMenu;
    private Button connectMenu;
    private TextView showTime;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (User) getIntent().getSerializableExtra("USER_OBJECT");
        setContentView(R.layout.set_target);

        Stats stats = new Stats(MainActivity.context);
        if(!stats.checkPermissions()) {
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 1);
        }

        //Set Time Button
        setTime = findViewById(R.id.target);
        showTime = findViewById(R.id.current_tar_time);
        setTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment dialogFragment = new TimePickFragment();
                dialogFragment.show(getSupportFragmentManager(),"Time Picker");

            }
        });

        //I'm Awake Button
        awake = findViewById(R.id.check_in);
        awake.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar currentCalendar = Calendar.getInstance();
                Stats stats = new Stats(MainActivity.context);
                stats.setTime(currentCalendar.getTimeInMillis());
                long timeSleeping = stats.getTotalTime(stats.getLastTime());
                long entireTime = stats.getTotalTime(user.getIdealHour(),user.getIdealMinute());
                //subtract the two above to get time NOT sleeping
                stats.update(user.getUniqueID(), entireTime, timeSleeping, entireTime - timeSleeping);
            }
        });

        dataMenu = findViewById(R.id.data);
        dataMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Home.this, MainActivity.class );
                intent.putExtra("USER_OBJECT", user);
                startActivity(intent);
            }
        });

        connectMenu = findViewById(R.id.misc);
        connectMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Home.this, Connect.class );
                intent.putExtra("USER_OBJECT", user);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        user.setIdealHour(hourOfDay);
        user.setIdealMinute(minute);
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
