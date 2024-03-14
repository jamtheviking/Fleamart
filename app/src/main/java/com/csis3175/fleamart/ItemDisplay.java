package com.csis3175.fleamart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        Intent intent = new Intent(getIntent());
        itemName.setText(intent.getStringExtra("itemName"));
        itemPrice.setText(String.valueOf(intent.getDoubleExtra("itemPrice",0)));
        itemDesc.setText(intent.getStringExtra("itemDesc"));
        itemImg.setImageResource(intent.getIntExtra("imgId",0));


    }
}