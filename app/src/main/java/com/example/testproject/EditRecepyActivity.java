package com.example.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject.Entities.ProductEntity;
import com.example.testproject.Entities.RecepyEntity;

import java.util.ArrayList;

public class EditRecepyActivity extends AppCompatActivity {
    private RecyclerView editRecepyRecyclerView;
    private RecepyProductsAdapter productsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recepy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editRecepyRecyclerView = findViewById(R.id.productsInRecepyRecycleView);
        layoutManager = new LinearLayoutManager(this);

        EditText editRecepyNameText = findViewById(R.id.editRecepyText);
        EditText editRecepyDescText = findViewById(R.id.editRecepyDescText);
        Button editRecepyButton = findViewById(R.id.recepyEditButton);
        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());

        Intent gotIntent = getIntent();
        RecepyEntity gotRecepyEntity = (RecepyEntity) gotIntent.getSerializableExtra("resultEntity");
        ArrayList<ProductEntity> allProducts = dbHelper.getAllProductsOfRecepy(gotRecepyEntity.getId());


        editRecepyNameText.setText(gotRecepyEntity.getRecepyName());
        editRecepyDescText.setText(gotRecepyEntity.getRecepyDescription());


        editRecepyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                    (!(editRecepyNameText.getText().toString().equals(gotRecepyEntity.getRecepyName()))) ||
                    (!(editRecepyDescText.getText().toString().equals(gotRecepyEntity.getRecepyDescription()))) &&
                    (editRecepyNameText.getText().toString().length() > 3 && editRecepyDescText.getText().toString().length() > 5)
                )
                {
                    gotRecepyEntity.setRecepyName(editRecepyNameText.getText().toString());
                    gotRecepyEntity.setRecepyDescription(editRecepyDescText.getText().toString());
                    dbHelper.editRecepy(gotRecepyEntity);
                    Toast toastSuccess = Toast.makeText(getApplicationContext(), "Recepy was edited successfully!", Toast.LENGTH_LONG);
                    toastSuccess.show();
                    Intent returnToMain = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(returnToMain);
                }
                else
                {
                    Toast toastFail = Toast.makeText(getApplicationContext(), "You have not changed anything in the recepy, or left the description or title too short!", Toast.LENGTH_LONG);
                    toastFail.show();
                }
            }
        });

        productsAdapter = new RecepyProductsAdapter(allProducts);
        editRecepyRecyclerView.setLayoutManager(layoutManager);
        editRecepyRecyclerView.setAdapter(productsAdapter);

        productsAdapter.setOnClickListenerProperty(new RecepyProductsAdapter.onClickListenerInterface() {
            @Override
            public void onDeleteButtonClick(int position) {
                ProductEntity productForRemoval = allProducts.get(position);
                dbHelper.removeProductFromRecepy(productForRemoval.getID());
                allProducts.remove(position);
                productsAdapter.notifyItemRemoved(position);
            }

            public void onEditButtonClick(int positionOfElement) {
                ProductEntity productForEdit = allProducts.get(positionOfElement);
                Intent productEdit = new Intent(getApplicationContext(), EditProductActivity.class);
                productEdit.putExtra("editProductEntity", productForEdit);
                startActivity(productEdit);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searcher_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.searchProductSearchView);
        SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String writtenText) {
                productsAdapter.getFilter().filter(writtenText);
                return false;
            }
        });
        return true;
    }
}
