package com.example.andrewzhao.myapplication;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.R.drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;


public class MainActivity extends AppCompatActivity {
    int requestForPerms = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_target);
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (!(mode == AppOpsManager.MODE_ALLOWED)) {
            requestForPerms = 1;
        }
        startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), requestForPerms);
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar currentCalendar = Calendar.getInstance();
        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(currentCalendar.getTimeInMillis()- 86400000, currentCalendar.getTimeInMillis());
        // DrawableUsageStatsMap is going to have the icon of the app with the usage states so that it can be easily displayed
        Map<Drawable, UsageStats> drawableUsageStatsMap = new HashMap<>();
        for (Map.Entry<String, UsageStats> entry : lUsageStatsMap.entrySet()) {
            Log.d("KEYS",("Entry keys: " + entry.getKey()));
            try {
                Drawable appIcon = this.getPackageManager()
                        .getApplicationIcon(entry.getKey());
                drawableUsageStatsMap.put(appIcon, entry.getValue());
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("ERROR", String.format("App Icon is not found for %s",
                        entry.getKey()));
                drawableUsageStatsMap.put(this.getDrawable(drawable.ic_delete), entry.getValue());

            }
        }
        LineChart chart = (LineChart) findViewById(R.id.chart);

        // The key is the hours of sleep gotten, and the value is the day of the week
        Map<Float, Float> timesMap = new HashMap<>();
        timesMap.put(7.5F, 1.0F);
        timesMap.put(6.5F, 2.0F);
        timesMap.put(8.5F, 3F);
        List<Entry> entries = new ArrayList<>();
        for (Map.Entry<Float, Float> singleTime : timesMap.entrySet()) {
            entries.add(new Entry(singleTime.getKey(), singleTime.getValue()));
        }



    }
}