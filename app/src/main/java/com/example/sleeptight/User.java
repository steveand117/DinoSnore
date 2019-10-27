package com.example.sleeptight;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class User {
    private String uniqueID = "";
    private int idealHour;
    private int idealMinute;
    private static int userNumber = 0;

    /**
     * Creates a new User
     * Pushes a generic user to FireBase
     */
    public User() {
        DatabaseReference temp = FirebaseDatabase.getInstance().getReference().push();
        HashMap<String, Integer> dataMap = new HashMap<>();
        dataMap.put("idealMinute", 0);
        dataMap.put("idealHour", 0);
        temp.setValue(dataMap);
        uniqueID = temp.getKey();
        idealMinute = 0;
        idealHour = 0;
    }

    public void setIdealHour(int hour){
        idealHour = hour;
    }

    public int getIdealHour(){
        return idealHour;
    }

    public int getIdealMinute(){
        return idealMinute;
    }

    public void setIdealMinute(int minute) {
        idealMinute = minute;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
