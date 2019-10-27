package com.example.sleeptight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sleeptight.MainActivity;
import com.example.sleeptight.R;
import com.example.sleeptight.User;

public class Connect extends AppCompatActivity {
    private Button dataMenu;
    private Button setTimeMenu;
    private User user;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (User) getIntent().getSerializableExtra("USER_OBJECT");
        setContentView(R.layout.connect);

        dataMenu = findViewById(R.id.login);
        dataMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Connect.this, MainActivity.class );
                intent.putExtra("USER_OBJECT", user);
                startActivity(intent);
            }
        });

        setTimeMenu = findViewById(R.id.login);
        setTimeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Connect.this, Home.class );
                intent.putExtra("USER_OBJECT", user);
                startActivity(intent);
            }
        });

    }
}