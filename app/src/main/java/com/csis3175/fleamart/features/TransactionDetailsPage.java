package com.csis3175.fleamart.features;import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.TransacationsAdapter;
import com.csis3175.fleamart.model.Transaction;
import com.csis3175.fleamart.model.User;

public class TransactionDetailsPage extends AppCompatActivity {


    int itemId;

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
    Button btnCancelTransaction;
    boolean fromViewOrderHistory;


    SharedPreferences sharedPreferences;

    DatabaseHelper db;
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
        btnCancelTransaction = findViewById(R.id.btnCancelTransaction);
        ivItemImage_Transaction = findViewById(R.id.ivItemImage_Transaction);

        db = new DatabaseHelper(TransactionDetailsPage.this);
        Intent intent = getIntent(); //Received from Card Adapter



        if (intent != null) {
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            userId = sharedPreferences.getInt("userId",0);
            transaction = (Transaction) intent.getSerializableExtra("transaction");
        }

        String name;
        if (userId == transaction.getSellerId()){
            //Returns the name of the buyer on the Sales View when the current user is the seller
            name = "Buyer:       " + db.getUsernameByID(transaction.getBuyerId());
        } else {
            //Returns the name of the seller on the Orders View when the current user is the buyer
            name = "Seller:      " + db.getUsernameByID(transaction.getSellerId());
        }

        tvTransactionId.setText(String.valueOf(transaction.getTransactionId()));
        tvItemName.setText(transaction.getItemName());
        tvItemPrice.setText(String.valueOf(transaction.getItemPrice()));
        tvPickUpDelivery.setText(transaction.getDelivery());
        tvBuyerName.setText(name);
        tvItemStatus.setText(transaction.getStatus());
        Glide.with(this)
                .load(transaction.getImageData())
                .into(ivItemImage_Transaction);




        btnSendNotification.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                boolean successItem = db.updateItemStatus(transaction.getItemId(), "sold");
                boolean successTransaction = db.updateTransactionStatus(transaction.getTransactionId(), "finalized");

                String notificationMessage;
                notificationMessage = String.format("Transaction ID %s with %s has been %s", transaction.getTransactionId(), transaction.getItemName(), "finalized");

                db.insertNotification(notificationMessage, transaction.getTransactionId(), transaction.getBuyerId(), transaction.getSellerId());


                Intent updateIntent = new Intent(TransactionDetailsPage.this, HomePage.class);
                updateIntent.putExtra("user", userId);
                if (successItem && successTransaction) {
                    Toast.makeText(TransactionDetailsPage.this, "item status updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TransactionDetailsPage.this, "item status failed", Toast.LENGTH_SHORT).show();
                }
                startActivity(updateIntent);
            }

        });

        /**
         * Method to cancel transaction via Button Cancel Transaction
         */
        if ("finalized".equals(transaction.getStatus()) || transaction.getBuyerId() == userId) {

            btnSendNotification.setVisibility(View.INVISIBLE);
        } else {
            btnSendNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper db = new DatabaseHelper(TransactionDetailsPage.this);
                    boolean successItem = db.updateItemStatus(transaction.getItemId(), "sold");
                    boolean successTransaction = db.updateTransactionStatus(transaction.getItemId(), "finalized");
                    Intent updateIntent = new Intent(TransactionDetailsPage.this, HomePage.class);
                    updateIntent.putExtra("user", userId);
                    if (successItem && successTransaction) {
                        Toast.makeText(TransactionDetailsPage.this, "item status updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TransactionDetailsPage.this, "item status failed", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(updateIntent);
                }

            });

        }
    }
}