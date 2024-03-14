package com.csis3175.fleamart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Sell extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        Button btnPlaceForSelling = findViewById(R.id.btnPlaceForSelling);
        btnPlaceForSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etItemName = findViewById(R.id.etItemName);
                EditText etItemPrice = findViewById(R.id.etItemPrice);
                EditText etItemDescription = findViewById(R.id.etItemDescription);
                EditText etItemLocation = findViewById(R.id.etItemLocation);
                Spinner etItemCategory = findViewById(R.id.spinnerCategories);
                EditText etItemTags = findViewById(R.id.etItemTags);

                String itemName = etItemName.getText().toString();
                String itemPrice = etItemPrice.getText().toString();
                String itemDescription = etItemDescription.getText().toString();
                String itemLocation = etItemLocation.getText().toString();
                String itemCategory = etItemCategory.getSelectedItem().toString();
                String itemTags = etItemTags.getText().toString();



                Users dbHelper = new Users(Sell.this);
                dbHelper.insertItem(itemName, itemPrice, itemDescription, itemLocation, itemCategory, itemTags);

                Toast.makeText(Sell.this, "Item placed for selling", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}