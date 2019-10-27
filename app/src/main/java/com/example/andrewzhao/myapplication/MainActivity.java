package com.example.andrewzhao.myapplication;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.R.drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;


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
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), requestForPerms);
        }
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
        Description desc = new Description();
        desc.setText("");
        chart.setDescription(desc);
        chart.setGridBackgroundColor(rgb("#A277C4"));
        chart.setDrawGridBackground(true);
        chart.setDrawBorders(true);
        // timesMap is the mock data
        // The key is the hours of sleep gotten, and the value is the day of the week
        Map<Float, Float> timesMap = new HashMap<>();
        timesMap.put(-3F, 1.0F);
        timesMap.put(2F, 2.0F);
        timesMap.put(-0.5F, 3F);
        timesMap.put(-1F, 4F);
        timesMap.put(0F, 5F);
        timesMap.put(-0.2F, 6F);
        timesMap.put(-0.3F, 0F);
        List<Entry> entries = new ArrayList<>();
        // For an 8 hour recommendation
        List<Entry> baselineSleep = new ArrayList<>();
        baselineSleep.add(new Entry(0F,0F));
        baselineSleep.add(new Entry(6F,0F));
        for (Map.Entry<Float, Float> singleTime : timesMap.entrySet()) {
            entries.add(new Entry(singleTime.getValue(), singleTime.getKey()));
        }
        Collections.sort(entries, new EntryXComparator());
        // the labels that should be drawn on the XAxis
        final String[] weekdays = new String[] { "Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun" };
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return weekdays[(int) value];
            }
        };
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        YAxis yAxis = chart.getAxisLeft();
        xAxis.setValueFormatter(formatter);
        LineDataSet dataSet = new LineDataSet(entries, null); // add entries to dataset
        LineDataSet baseLineSleepDataSet = new LineDataSet(baselineSleep, null);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSet.setColor(rgb("#7339A0"));
        dataSet.setValueTextColor(rgb("#7339A0")); // styling, ...
        dataSet.setCircleColor(rgb("#7339A0"));
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4f);
        dataSet.setValueTextSize(12f);
        baseLineSleepDataSet.setLineWidth(2.5f);
        baseLineSleepDataSet.setCircleRadius(4f);
        baseLineSleepDataSet.setColor(rgb("#FEFEF4"));
        baseLineSleepDataSet.setValueTextColor(rgb("#FEFEF4"));
        baseLineSleepDataSet.setCircleColor(rgb("#FEFEF4"));
        dataSets.add(dataSet);
        dataSets.add(baseLineSleepDataSet);
        LineData lineData = new LineData(dataSets);
        lineData.setDrawValues(false);

        chart.setData(lineData);
        chart.getLegend().setEnabled(false);
        chart.invalidate(); // refresh


    }
    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }
}
