package com.example.sleeptight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sleeptight.R;
import com.example.sleeptight.User;

public class Data extends AppCompatActivity {
    private User user;
    private Button setMenu;
    private Button connectMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (User) getIntent().getSerializableExtra("USER_OBJECT");
        setContentView(R.layout.data_display);

        setMenu = findViewById(R.id.set);
        setMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Data.this, Home.class );
                intent.putExtra("USER_OBJECT", user);
                startActivity(intent);
            }
        });

        connectMenu = findViewById(R.id.misc);
        connectMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Data.this, Connect.class );
                intent.putExtra("USER_OBJECT", user);
                startActivity(intent);
            }
        });
    }


}
