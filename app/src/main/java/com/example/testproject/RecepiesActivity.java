package com.example.testproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.testproject.Entities.RecepyEntity;

import java.util.ArrayList;

public class RecepiesActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    ListView usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recepies);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usersList = findViewById(R.id.allUsersListView);
        dbHelper = new DatabaseHelper(getBaseContext());
        ArrayList<RecepyEntity> recepiesEntityList = dbHelper.getAllRecepies();

        RecepiesAdapter newRecepiesAdapter = new RecepiesAdapter(this, R.layout.list_item_recepies, recepiesEntityList);
        usersList.setAdapter(newRecepiesAdapter);

    }
}
