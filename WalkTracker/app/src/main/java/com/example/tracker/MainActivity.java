package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button map,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = findViewById(R.id.button);
        data = findViewById(R.id.button3);

        map.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startTracking(view);
            }

        });

        data.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(), DataEntryActivity.class);
                startActivity(i);
            }

        });
    }

    public void startTracking(View view){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Location permission revoked, please reinstate the permission to continue",Toast.LENGTH_LONG).show();
        }
        else {

                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);

            }
        }
    }
