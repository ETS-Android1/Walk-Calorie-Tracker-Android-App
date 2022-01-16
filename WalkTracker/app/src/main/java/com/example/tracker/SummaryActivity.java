package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    TextView dist, timing, speed, est_cals;
    Button toHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        dist = findViewById(R.id.textView3);
        timing = findViewById(R.id.textView7);
        speed = findViewById(R.id.textView9);
        est_cals = findViewById(R.id.textView11);
        toHome = findViewById(R.id.button6);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        Bundle extras = getIntent().getExtras();

        double tot_dist = extras.getDouble("Dist");
        float display_dist = 0;
        if (tot_dist > 1000) {
            display_dist = Math.round(tot_dist/10)/100;
            dist.setText(String.valueOf(display_dist) + "km");
        }
        else {
            display_dist = Math.round(tot_dist);
            dist.setText(String.valueOf(display_dist) + "m");
        }
        int tot_time = extras.getInt("Timer");
        timing.setText(String.valueOf(tot_time));
        long avg_speed = 0;
        if (tot_time != 0)
            avg_speed = Math.round((tot_dist/tot_time)*60/1000) ;
        speed.setText(String.valueOf(avg_speed));

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        float weight = sharedPreferences.getFloat("Weight",0);
        float met = (float)(avg_speed * 0.71);
        int calories = Math.round((float)(tot_time * 3.5 * met * weight/200));
        est_cals.setText(String.valueOf(calories));


    }
}