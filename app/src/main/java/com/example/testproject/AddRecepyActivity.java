package com.example.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testproject.Entities.RecepyEntity;

public class AddRecepyActivity extends AppCompatActivity {

    EditText recepyTextField;
    EditText recepyDescriptionTextField;
    Button addRButton;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recepy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recepyTextField = findViewById(R.id.recepyTextField);
        recepyDescriptionTextField = findViewById(R.id.recepyDescriptionTextField);
        addRButton = findViewById(R.id.addRButton);


        recepyTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (recepyTextField.getText().toString().length() < 3)
                {
                    recepyTextField.setError("This field should contain less than 3 symbols");
                }
                else
                {
                    recepyTextField.setError(null);
                }
            }
        });

        recepyDescriptionTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (recepyDescriptionTextField.getText().toString().length() < 5)
                {
                    recepyDescriptionTextField.setError("The description field should contain more than 5 symbols");
                }
                else
                {
                    recepyDescriptionTextField.setError(null);
                }
            }
        });



        addRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recepyTextField.getText().toString().length() < 3)
                {
                    Toast errorToast = Toast.makeText(getApplicationContext(),
                            "Recepy text should be more than 3 symbols",
                            Toast.LENGTH_LONG);
                    errorToast.show();
                }
                else if (recepyDescriptionTextField.getText().toString().length() < 5)
                {
                    Toast errorToast = Toast.makeText(getApplicationContext(),
                            "Recepy description should be more than 5 symbols",
                            Toast.LENGTH_LONG);
                    errorToast.show();
                }
                else if(recepyTextField.getText().toString().length() < 3 && recepyDescriptionTextField.getText().toString().length() < 5)
                {
                    Toast errorToast = Toast.makeText(getApplicationContext(),
                            "Recepy description should be more than 5 symbols.\nAnd recepy name should be more than 3 symbols",
                            Toast.LENGTH_LONG);
                    errorToast.show();
                }
                else
                {
                    dbHelper = new DatabaseHelper(getBaseContext());
                    long resultedId = dbHelper.addRecepy(recepyTextField.getText().toString(), recepyDescriptionTextField.getText().toString());
                    RecepyEntity resultedRecepyEntity = dbHelper.findRecepyById(resultedId);
                    Toast successToast = Toast.makeText(getApplicationContext(), "Added recepy successfully to the recepy list!", Toast.LENGTH_LONG);
                    successToast.show();
                    Intent sendToAddProducts = new Intent(getBaseContext(), AddProductsToRecepyActivity.class);
                    sendToAddProducts.putExtra("RecepyEntitySent", resultedRecepyEntity);
                    startActivity(sendToAddProducts);
                }
            }
        });
    }
}
