package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
public class DataEntryActivity extends AppCompatActivity {

    private EditText weightEntry;
    private Button saveButton;
    private Button toHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        weightEntry = (EditText) findViewById(R.id.editTextNumber);
        saveButton = (Button) findViewById(R.id.button4);
        toHome = (Button) findViewById(R.id.button5);

        Toast.makeText(this,"Your weight is needed to calculate your calories burnt",Toast.LENGTH_LONG).show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome(view);
            }
        });


    }

    public void goHome(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        if (sharedPreferences.getFloat("Weight",0) == 0){
            Toast.makeText(this, "Please save your weight", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try{
            float weight = Float.parseFloat(String.valueOf(weightEntry.getText()));
            editor.putFloat("Weight", weight);
            editor.apply();
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show();
        }

    }

}