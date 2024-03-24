package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.User;

public class OrderConfirmation extends AppCompatActivity {
    int itemid = 0;
    Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        //https://tutorialwing.com/create-an-android-seekbar-programmatically-in-android/

        //Todo Ask scene1>User to select pick up or delivery => scene2> confirm order and price => back to home
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("item")) { // Use "testitemid" as the key
            item = (Item) intent.getSerializableExtra("item");
            //Populate layout
            TextView output = findViewById(R.id.output);
            output.setText(String.valueOf(itemid));
        } else {
            Log.d("Order", "Item ID not found in intent");
            // Handle the case where item ID is not passed
        }




    }
}