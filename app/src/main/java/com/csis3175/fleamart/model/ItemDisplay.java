package com.csis3175.fleamart.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;

public class ItemDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        TextView itemName = findViewById(R.id.itemName);
        TextView itemPrice = findViewById(R.id.itemPrice);
        TextView itemDesc = findViewById(R.id.itemDesc);
        ImageView itemImg = findViewById(R.id.itemImg);

        //Receive data from CardAdapter
        Intent intent = getIntent();
        itemName.setText(intent.getStringExtra("itemName"));
        itemPrice.setText(String.valueOf(intent.getDoubleExtra("itemPrice",0)));
        itemDesc.setText(intent.getStringExtra("itemDesc"));
        byte[] imageData = intent.getByteArrayExtra("imgData");

        Log.d("ImageDebug", "Image Path: " + imageData);

        //Using GLIDE to receive and push image
        Glide.with(ItemDisplay.this)
                .load(imageData)
                .into(itemImg);

    }
}