package com.example.sleeptight;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Stats {
    private UsageStatsManager mUsageStatsManager;
    private static long time;
    private final Context context;
    private DatabaseReference mDataBase;

    public Stats(Context context) {
        this.context = context;
        mUsageStatsManager = (UsageStatsManager) context.getSystemService(android.content.Context.USAGE_STATS_SERVICE);
        mDataBase = FirebaseDatabase.getInstance().getReference();
    }

    public void setTime(long time){
        this.time = time;
        System.out.println(time);
    }

    /**
     * Updates the Database with the most recent information
     * @param uniqueID identifying ID for the user
     * @param lengthOfTime milliseconds since ideal sleeping time
     * @return true if updated
     */
    public boolean update(String uniqueID, long lengthOfTime, long timeSleeping, long timeAwake) {
        HashMap<String, Long> dataMap = new HashMap<>();
        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(time - lengthOfTime, time);
        for (Map.Entry<String, UsageStats> entry : lUsageStatsMap.entrySet()) {
            String[] temp = entry.getKey().split("\\.");
            if(entry.getValue().getTotalTimeInForeground() != 0){
                dataMap.put(temp[temp.length-1], entry.getValue().getTotalTimeInForeground());
            }

        }
        dataMap.put("timeAwake", timeAwake);
        dataMap.put("timeSleeping", timeSleeping);
        dataMap.put("timeWokenUp", time);

        try {
            mDataBase.child(uniqueID).child("data").child(String.valueOf(time)).setValue(dataMap);
            return true;
        } catch (Exception e) {
            System.out.println("Reached an exception: " + e);
            return false;
        }

    }

    /**
     * Gets the most recent use of a program within the past 24 hours
     * @return millisecond representation of time
     */
    public long getLastTime() {
        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(time - 80000000,time);
        long lastTime = Long.MIN_VALUE;
        for (Map.Entry<String, UsageStats> entry : lUsageStatsMap.entrySet()) {
            if(entry.getValue().getLastTimeForegroundServiceUsed() > lastTime) {
                lastTime = entry.getValue().getLastTimeForegroundServiceUsed();
            }
        }
        return lastTime;
    }

    /**
     * Returns the total amount of time since user inputted ideal time
     * @param hour hour of ideal time
     * @param minutes minutes of ideal time
     * @return total time in milliseconds
     */
    public long getTotalTime(int hour, int minutes) {
        Calendar temp = Calendar.getInstance();
        //int year,int month,int day,int hourOfDay, int minute,int second)
        if(hour < 12) {
            temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH), hour, minutes);
        } else {
            temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH) - 1, hour, minutes);
        }
        return time - temp.getTimeInMillis();
    }

    public long getTotalTime(long time){
        return this.time - time;
    }

    /**
     * Determines if the user has provided the app permissions
     * @return true if given
     */
    public boolean checkPermissions(){
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }
}
