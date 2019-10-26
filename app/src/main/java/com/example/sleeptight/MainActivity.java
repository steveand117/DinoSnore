package com.example.sleeptight;

import android.app.TimePickerDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private Button mFireBaseButton;
    private DatabaseReference mDatabase;
    private TextView showTime;
    private Button showDialog;

    private String username;
    private String password;
    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int requestForPerms = 1; // set this to zero after you're done with it
        startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), requestForPerms);
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(currentCalendar.getTimeInMillis()- 86400000, currentCalendar.getTimeInMillis());
        for (Map.Entry<String, UsageStats> entry : lUsageStatsMap.entrySet()) {
            System.out.println(entry.getKey() + " used in foreground for " + entry.getValue().getTotalTimeInForeground());
            System.out.println(TimeUnit.MILLISECONDS.toSeconds(entry.getValue().getTotalTimeInForeground()));

        }

        mFireBaseButton = findViewById(R.id.firebase_btn);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mFireBaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = ((EditText) findViewById(R.id.txt_username)).getText().toString();
                password = ((EditText) findViewById(R.id.txt_password)).getText().toString();
                time = ((EditText) findViewById(R.id.time_sleep)).getText().toString();
                HashMap<String, String> dataMap = new HashMap<>();
                dataMap.put("username",username);
                dataMap.put("password", password);
                dataMap.put("time", time);

                mDatabase.push().setValue(dataMap);
            }
        });

        showDialog = findViewById(R.id.txt_showDialog);
        showTime = findViewById(R.id.txt_showTime);
        showDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment dialogFragment = new TimePickFragment();
                dialogFragment.show(getSupportFragmentManager(),"Time Picker");

            }
        });



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        showTime.setText("Hour" + hourOfDay + " Minutes" + minute);
    }


}
