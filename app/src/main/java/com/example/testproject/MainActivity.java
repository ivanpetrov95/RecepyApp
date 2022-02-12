package com.example.testproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button viewRecepiesButton;
    Button addRecepyButton;
    Button productsListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewRecepiesButton = findViewById(R.id.viewRecepiesButton);
        addRecepyButton = findViewById(R.id.addRecepyButton);
        productsListButton = findViewById(R.id.productsListButton);

        viewRecepiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                Intent recepyActivityIntent = new Intent(getBaseContext(), RecepiesActivity.class);
                startActivity(recepyActivityIntent);
            }
        });

        addRecepyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recepyAddIntent = new Intent(getBaseContext(), AddRecepyActivity.class);
                startActivity(recepyAddIntent);
            }
        });

        productsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productsListIntent = new Intent(getBaseContext(), RemovingProductActivity.class);
                startActivity(productsListIntent);
            }
        });

    }
}
