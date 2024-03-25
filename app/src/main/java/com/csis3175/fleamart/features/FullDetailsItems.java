package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.User;

import java.text.DecimalFormat;

public class FullDetailsItems extends AppCompatActivity {

    int userId;
    User user;

    EditText itemName;
    EditText itemPrice;
    EditText itemDiscount;
    EditText itemDescription;

    EditText itemLocation;
    EditText itemCategory;

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DecimalFormat df = new DecimalFormat("0.00");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details_items);
        Button btnUpdateItem = findViewById(R.id.btnUpdateItem);
        itemName = findViewById(R.id.EdTxtItemName);
        itemPrice = findViewById(R.id.EdTxtPrice);
        itemDiscount = findViewById(R.id.EdTxtDiscount);
        itemDescription = findViewById(R.id.EdTxtDesc);
        itemLocation = findViewById(R.id.EdTxtLocation);
        itemCategory = findViewById(R.id.EdTxtCategory);
        imageView = findViewById(R.id.ImageView);



          Intent intent = getIntent();
         if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");

         }

        if (getIntent().hasExtra("selectedItem")) {
          Item selectedItem = (Item) getIntent().getSerializableExtra("selectedItem");


            assert selectedItem != null;
            itemName.setText(selectedItem.getItemName());
            itemPrice.setText(String.valueOf(selectedItem.getItemPrice()));
            double discount = 1 - selectedItem.getDiscount();
            itemDiscount.setText(String.valueOf(df.format(discount)));
            itemDescription.setText(selectedItem.getItemDescription());
            itemLocation.setText(selectedItem.getLocation());
            itemCategory.setText(selectedItem.getCategory());
            // byte[] imageData = selectedItem.getImageData();
            // Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            // imageView.setImageBitmap(bitmap);
            // attempting to add an image

        }

        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    updateItem(v);
                    Intent updateIntent = new Intent(FullDetailsItems.this, HomePage.class);
                    updateIntent.putExtra("user", user);
                    startActivity(updateIntent);
                } else {
                    Toast.makeText(FullDetailsItems.this, "User data is miss", Toast.LENGTH_LONG).show();
                }
            }

        });


    }


public void updateItem(View view) {

    Item updateItem = new Item();

    String upName = itemName.getText().toString();
    double upPrice = Double.parseDouble(itemPrice.getText().toString());
    double upDiscount = Double.parseDouble(itemDiscount.getText().toString());
    String upDesc = itemDescription.getText().toString();
    String upLocation = itemLocation.getText().toString();
    String upCategory = itemCategory.getText().toString();

    updateItem.setItemName(upName);
    updateItem.setItemPrice(upPrice);
    updateItem.setDiscount(upDiscount);
    updateItem.setItemDescription(upDesc);
    updateItem.setLocation(upLocation);
    updateItem.setCategory(upCategory);


    String updateName = updateItem.getItemName();
    Double updatePrice = updateItem.getItemPrice();
    Double updateDiscount = updateItem.getDiscount();
    String updateDesc = updateItem.getItemDescription();
    String updateLocation = updateItem.getLocation();
    String updateCategory = updateItem.getCategory();

    userId = user.getId();
    DatabaseHelper db = new DatabaseHelper(this);
    boolean updateItems = db.updateItem(userId,updateName, updatePrice, updateDiscount, updateDesc, updateLocation, updateCategory);

    if (user == null)
    { Toast.makeText(this, "An error occurred. User information is missing.", Toast.LENGTH_LONG).show();
    }
    if (updateItems) {
        Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
    }
    else
        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
    }
}