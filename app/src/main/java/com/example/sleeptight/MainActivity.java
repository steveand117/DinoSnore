package com.example.sleeptight;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sleeptight.ui.Home;
import com.google.firebase.database.Query;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView signUpBtn;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.landing);

        signUpBtn = findViewById(R.id.login);
        signUpBtn.setOnClickListener(new View.OnClickListener(){
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
