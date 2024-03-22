package com.csis3175.fleamart.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemDisplay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        TextView itemName = findViewById(R.id.itemName);
        TextView itemPrice = findViewById(R.id.itemPrice);
        TextView itemDesc = findViewById(R.id.itemDesc);
        ImageView itemImg = findViewById(R.id.itemImg);
        TextView itemListDay = findViewById(R.id.itemListDay);
        Button btnConfirm = findViewById(R.id.btConfirm);
        DecimalFormat df = new DecimalFormat("#$");

        Intent intent = getIntent(); //Received from Card Adapter
        int itemId;
        itemId = intent.getIntExtra("itemId", 0);



        DatabaseHelper db = new DatabaseHelper(this);
        Item item = db.getItemById(itemId);

        if (item != null) {
            itemName.setText(item.getItemName());
            itemPrice.setText(String.format(Locale.getDefault(), df.format(item.getItemPrice())));
            itemDesc.setText(item.getItemDescription());
            byte[] imageData = item.getImageData();
            boolean isShareable = item.getIsShareable();
            Date dateObj = convertToListedDate(item.getDate());
            itemListDay.setText(calculateListedDays(dateObj));

            if (!isShareable) {
                btnConfirm.setText(R.string.btnBorrow);
            }

            Glide.with(this)
                    .load(imageData)
                    //Todo placeholder image
//                    .placeholder(R.drawable.placeholder_image)
                    .into(itemImg);
        } else {
            Toast.makeText(this, "Failed to fetch item details", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnConfirm.setOnClickListener(v -> {
            // Handle button click logic here
        });
    }

    private Date convertToListedDate(String dateString) {
        Date currentDate = null;
        Locale locale = Locale.getDefault();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        try {
            currentDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            Toast.makeText(this, "Failed to parse date", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return currentDate;
    }

    private String calculateListedDays(Date convertedDate) {
        if (convertedDate == null) {
            return "Listed date not available";
        }

        Date currentDate = new Date();
        long diffInMill = Math.abs(currentDate.getTime() - convertedDate.getTime());
        long diffInDays = diffInMill / (24 * 60 * 60 * 1000);
        return "Listed " + diffInDays + " days ago";
    }

}