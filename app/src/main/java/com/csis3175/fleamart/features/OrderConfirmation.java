package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.User;

public class OrderConfirmation extends AppCompatActivity {
    int itemid = 0;
    Item item;
    private ImageButton toggleDeliver,togglePickup;
    public  Button btConfirm,btCancel;
    public boolean isDeliverySelected = false;
    ImageView itemImg;
    TextView itemName,itemPrice;
    byte[] imageData;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        //https://tutorialwing.com/create-an-android-seekbar-programmatically-in-android/
        toggleDeliver = findViewById(R.id.toggleDeliver);
        togglePickup = findViewById(R.id.togglePickup);
        btConfirm = findViewById(R.id.btConfirm);
        btCancel = findViewById(R.id.btCancel);
        itemImg = findViewById(R.id.itemImg);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);

        //Todo Ask scene1>User to select pick up or delivery => scene2> confirm order and price => back to home
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("item")) { // Use "testitemid" as the key
            item = (Item) intent.getSerializableExtra("item");
            //Populate layout
            imageData = item.getImageData();
            itemName.setText(item.getItemName());
            itemPrice.setText(String.valueOf(item.getItemPrice()));

            Glide.with(this)
                    .load(imageData)
                    .into(itemImg);


        } else {
            Log.d("Order", "Item ID not found in intent");
            // Handle the case where item ID is not passed
        }

        onToggleDeliver();
        onTogglePickup();


        toggleDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDeliverySelected) {
                    isDeliverySelected = true;
                    toggleDeliver.setImageResource(R.drawable.delivery);
                    togglePickup.setImageResource(R.drawable.pickup_false);
                }
                Log.d("Toggle", "toggle value is "+isDeliverySelected);
            }
        });

        togglePickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDeliverySelected) {
                    isDeliverySelected = false;
                    toggleDeliver.setImageResource(R.drawable.delivery_false);
                    togglePickup.setImageResource(R.drawable.pickup);
                } else {
                    isDeliverySelected = false;
                    togglePickup.setImageResource(R.drawable.pickup);
                }
                Log.d("Toggle", "toggle value is "+isDeliverySelected);
            }
        });



        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void onToggleDeliver() {
        // Change color of imageButton2 when imageButton1 is clicked
        toggleDeliver.setImageResource(R.drawable.delivery_false);
    }

    public void onTogglePickup() {
        // Change color of imageButton1 when imageButton2 is clicked
        togglePickup.setImageResource(R.drawable.pickup_false);
    }


}