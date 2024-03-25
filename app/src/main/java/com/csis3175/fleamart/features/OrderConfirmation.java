package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.*;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.ItemDisplay;
import com.csis3175.fleamart.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderConfirmation extends AppCompatActivity {
    public  Button btConfirm,btCancel;
    public boolean isDeliverySelected = false;

    int buyerId =0;
    String currentDate,deliveryMethod;
    Item item;
    ImageView itemImg;
    TextView itemName,itemPrice,msgConfirmation;
    byte[] imageData;
    private ImageButton toggleDeliver,togglePickup;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        //https://tutorialwing.com/create-an-android-seekbar-programmatically-in-android/
        toggleDeliver = findViewById(R.id.toggleDeliver);
        togglePickup = findViewById(R.id.togglePickup);
        btConfirm = findViewById(R.id.btConfirm);
        btCancel = findViewById(R.id.btCancel);
        msgConfirmation = findViewById(R.id.msgConfirmation);
        itemImg = findViewById(R.id.itemImg);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);

        //Todo Ask scene1>User to select pick up or delivery => scene2> confirm order and price => back to home
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("item")) { // Use "testitemid" as the key
            item = (Item) intent.getSerializableExtra("item");
            user = (User) intent.getSerializableExtra("user");

            //Populate layout
            imageData = item.getImageData();
            itemName.setText(item.getItemName());
            itemPrice.setText(String.valueOf(item.getItemPrice()));

            Glide.with(this)
                    .load(imageData)
                    .into(itemImg);
        } else {
            Log.d("Order", "Item ID not found in intent");

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
                    togglePickup.setImageResource(R.drawable.pickup);
                }
                Log.d("Toggle", "toggle value is "+isDeliverySelected);
            }
        });



        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                currentDate = dateFormat.format(new Date());
                if(isDeliverySelected){
                    deliveryMethod = "delivery";

                }
                else {
                    deliveryMethod = "Pickup";
                }
                DatabaseHelper db = new DatabaseHelper(OrderConfirmation.this);
                buyerId = user.getId();
                db.insertTransaction(buyerId,item.getUserID(),item.getItemID(),currentDate,deliveryMethod,"pending");
                db.updateItemStatus(item.getItemID(),"pending");
                msgConfirmation.setText(R.string.txtConfirmation);
                //Post Delay
                new Handler().postDelayed(() -> startActivity(new Intent(OrderConfirmation.this, HomePage.class)), 6000);
                startActivity(new Intent(OrderConfirmation.this, HomePage.class));

            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderConfirmation.this, ItemDisplay.class));
                Log.d("LOG USER ID","user id is"+user.getId());
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