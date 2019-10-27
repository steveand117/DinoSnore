package com.example.sleeptight;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable{
    private String uniqueID = "";
    private int idealHour;
    private int idealMinute;

    /**
     * Creates a new User
     * Pushes a generic user to FireBase
     */
    public User(){
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
        DatabaseReference temp = FirebaseDatabase.getInstance().getReference();
        System.out.println(temp.child(uniqueID).child("idealHour").setValue(hour));
        idealHour = hour;

    }

    public int getIdealHour(){
        return idealHour;
    }

    public int getIdealMinute(){
        return idealMinute;
    }

    public void setIdealMinute(int minute) {
        DatabaseReference temp = FirebaseDatabase.getInstance().getReference();
        System.out.println(temp.child(uniqueID).child("idealMinute").setValue(minute));
        idealMinute = minute;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
