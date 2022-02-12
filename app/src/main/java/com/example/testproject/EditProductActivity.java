package com.example.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject.Entities.ProductEntity;

public class EditProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        EditText editProduct = findViewById(R.id.editProductTextEdit);
        Button editProductButton = findViewById(R.id.productEditButton);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        Intent gotIntent = getIntent();
        ProductEntity receivedProductEntity = (ProductEntity) gotIntent.getSerializableExtra("editProductEntity");

        editProduct.setText(receivedProductEntity.getProductName());



        editProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editProduct.getText().toString().equals(receivedProductEntity.getProductName().toString()))
                {
                    receivedProductEntity.setProductName(editProduct.getText().toString());
                    dbHelper.editProduct(receivedProductEntity);
                    Toast successToast = Toast.makeText(getApplicationContext(), "You have edited product! Now return to the recepy.", Toast.LENGTH_LONG);
                    successToast.show();
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                }
                else
                {
                    Toast failToast = Toast.makeText(getApplicationContext(), "You have not changed the text of the product!", Toast.LENGTH_LONG);
                    failToast.show();
                }
            }
        });
    }
}
