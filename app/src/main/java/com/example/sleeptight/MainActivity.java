package com.example.sleeptight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.sleeptight.ui.Home;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);

        enterBtn = findViewById(R.id.enter);
        enterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                User owo = new User();
                NextActivity();
            }
        });

    }

    private void NextActivity(){
        Intent intent = new Intent(MainActivity.this, Home.class );
        startActivity(intent);
    }

}
