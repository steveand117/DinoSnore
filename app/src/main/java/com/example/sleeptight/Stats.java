package com.example.sleeptight;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Stats {
    private UsageStatsManager mUsageStatsManager;
    private static Calendar currentCalendar = Calendar.getInstance();
    private final Context context;
    private DatabaseReference mDataBase;

    public Stats(Context context) {
        this.context = context;
        mUsageStatsManager = (UsageStatsManager) context.getSystemService(android.content.Context.USAGE_STATS_SERVICE);
        mDataBase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Updates the Database with the most recent information
     * @param uniqueID identifying ID for the user
     * @param lengthOfTime milliseconds since ideal sleeping time
     * @return true if updated
     */
    public boolean update(String uniqueID, int lengthOfTime) {
        HashMap<String, Long> dataMap = new HashMap<>();
        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(currentCalendar.getTimeInMillis()- lengthOfTime
                , currentCalendar.getTimeInMillis());
        for (Map.Entry<String, UsageStats> entry : lUsageStatsMap.entrySet()) {
            String[] temp = entry.getKey().split("\\.");
            if(entry.getValue().getTotalTimeInForeground() != 0){
                dataMap.put(temp[temp.length-1], entry.getValue().getTotalTimeInForeground());
            }

        }

        try {
            mDataBase.child(uniqueID).child("data").child(String.valueOf(currentCalendar.getTime())).setValue(dataMap);
            return true;
        } catch (Exception e) {
            System.out.println("Reached an exception: " + e);
            return false;
        }

    }

    public long getLastTime() {
        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.queryAndAggregateUsageStats(currentCalendar.getTimeInMillis()- 80000000
                , currentCalendar.getTimeInMillis());
        long lastTime = Long.MIN_VALUE;
        for (Map.Entry<String, UsageStats> entry : lUsageStatsMap.entrySet()) {
            if(entry.getValue().getLastTimeForegroundServiceUsed() > lastTime) {
                lastTime = entry.getValue().getLastTimeForegroundServiceUsed();
            }
        }
        return lastTime;
    }

    /**
     * Determines if the user has provided the app permissions
     * @return true if given
     */
    public boolean checkPermissions(){
        int res = context.checkCallingOrSelfPermission(Settings.ACTION_USAGE_ACCESS_SETTINGS);

        return res == PackageManager.PERMISSION_GRANTED;
    }
}
