package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.Transaction;
import com.csis3175.fleamart.model.User;

public class TransactionDetailsPage extends AppCompatActivity {

    Item item;
    int itemId;
    private User user;
    int userId;

    Transaction transaction;

    TextView tvItemName;
    TextView tvTransactionId;
    TextView tvItemPrice;
    TextView tvPickUpDelivery;
    TextView tvItemStatus;
    TextView tvBuyerName;
    ImageView ivItemImage_Transaction;
    Button btnSendNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        tvItemName = findViewById(R.id.tvItemName);
        tvTransactionId = findViewById(R.id.tvTransactionId);
        tvItemPrice = findViewById(R.id.tvItemPrice);
        tvPickUpDelivery = findViewById(R.id.tvPickUpDelivery);
        tvItemStatus = findViewById(R.id.tvItemStatus);
        tvBuyerName = findViewById(R.id.tvBuyerName);
        btnSendNotification = findViewById(R.id.btnSendNotification);
        ivItemImage_Transaction = findViewById(R.id.ivItemImage_Transaction);

        Intent intent = getIntent(); //Received from Card Adapter
        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            userId = user.getId();
            item = (Item) intent.getSerializableExtra("item");
            transaction = (Transaction) intent.getSerializableExtra("transaction");
        }

        DatabaseHelper db = new DatabaseHelper(TransactionDetailsPage.this);
        String buyerName = db.getUsernameByID(transaction.getBuyerId());
        tvTransactionId.setText(String.valueOf(transaction.getTransactionId()));
        tvItemName.setText(item.getItemName());
        tvItemPrice.setText(String.valueOf(item.getItemPrice()));
        tvPickUpDelivery.setText(transaction.getDelivery());
        tvBuyerName.setText(buyerName);
        tvItemStatus.setText(transaction.getStatus());
        Glide.with(this).load(item.getImageData()).into(ivItemImage_Transaction);

    }
}