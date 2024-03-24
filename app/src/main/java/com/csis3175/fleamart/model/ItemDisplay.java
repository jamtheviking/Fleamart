package com.csis3175.fleamart.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.features.OrderConfirmation;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemDisplay extends AppCompatActivity {
    Item item;
    int itemId;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        //TODO REQUIREMENT: Display seller information in this page.

        TextView itemName = findViewById(R.id.itemName);
        TextView itemPrice = findViewById(R.id.itemPrice);
        TextView itemDesc = findViewById(R.id.itemDesc);
        ImageView itemImg = findViewById(R.id.itemImg);
        TextView itemListDay = findViewById(R.id.itemListDay);
        Button btnConfirm = findViewById(R.id.btConfirm);
        DecimalFormat df = new DecimalFormat("#$");


        Intent intent = getIntent(); //Received from Card Adapter
        item = (Item) intent.getSerializableExtra("item");
        itemId = item.getItemID();
        user = (User) intent.getSerializableExtra("user");


        DatabaseHelper db = new DatabaseHelper(this);
        //TODO modify db query. Join users table and retrieve Seller name and email?
        item = db.getItemById(itemId);

        if (item != null) {
            itemName.setText(item.getItemName());
            //TODO calculate discounted price or show discount
            double price = item.getItemPrice();
            double discount = item.getDiscount();
            double discountedPrice = price * (1 - discount);
            itemId = item.getItemID();
            itemPrice.setText(String.format(Locale.getDefault(), df.format(discountedPrice)));
            itemDesc.setText(item.getItemDescription());
            byte[] imageData = item.getImageData();
            //TODO add an identifier to a card that is shareable
            boolean isShareable = item.getIsShareable();
            Date dateObj = convertToListedDate(item.getDate());
            itemListDay.setText(calculateListedDays(dateObj));

            if (isShareable) {
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
            Log.d("ItemDisplay", "Item ID before passing: " + item.getItemID());

            Intent nextIntent = new Intent(ItemDisplay.this, OrderConfirmation.class);
            nextIntent.putExtra("item", item);
            nextIntent.putExtra("user",user);
            startActivity(nextIntent);
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