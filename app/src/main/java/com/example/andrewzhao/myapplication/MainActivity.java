package com.example.andrewzhao.myapplication;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int requestForPerms = 1; // set this to zero after you're done with it
        //startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), requestForPerms);
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(currentCalendar.getTimeInMillis()- 86400000, currentCalendar.getTimeInMillis());
        for (Map.Entry<String, UsageStats> entry : lUsageStatsMap.entrySet()) {
            Log.d("KEYS",("Entry keys: " + entry.getKey()));
        }
    }
}
