package com.example.sleeptight;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.sleeptight.ui.Home;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button enterBtn;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.landing);

        enterBtn = findViewById(R.id.enter);
        enterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                User owo = new User();
                NextActivity(owo);
            }
        });

    }

    private void NextActivity(User user){
        Intent intent = new Intent(MainActivity.this, Home.class );
        intent.putExtra("USER_OBJECT", user);
        startActivity(intent);
    }

}
