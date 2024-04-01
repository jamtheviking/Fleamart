package com.csis3175.fleamart.features;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.TransacationsAdapter;
import com.csis3175.fleamart.model.Transaction;

import java.util.ArrayList;

public class viewOrderStatus extends AppCompatActivity {
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    TextView buyerOrSeller;
    private int userId;
    SharedPreferences sharedPreferences;
    RecyclerView rvOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_status);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        rvOrderStatus = findViewById(R.id.rvOrderStatusView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        rvOrderStatus.setLayoutManager(gridLayoutManager);
        TransacationsAdapter transacationsAdapter = new TransacationsAdapter(viewOrderStatus.this, getAllTransactions());
        rvOrderStatus.setAdapter(transacationsAdapter);
    }


    private ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> allTransactions = new ArrayList<>();

        Cursor c = databaseHelper.viewStatusOrder(userId);
        allTransactions.addAll(processTransactionCursor(c));

        return allTransactions;
    }

    private ArrayList<Transaction> processTransactionCursor(Cursor c) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {
                int transactionId = c.getInt(c.getColumnIndexOrThrow("transaction_id"));
                int buyerId = c.getInt(c.getColumnIndexOrThrow("transaction_buyer_id"));
                int sellerId = c.getInt(c.getColumnIndexOrThrow("transaction_seller_id"));
                int itemId = c.getInt(c.getColumnIndexOrThrow("itemid"));
                String date = c.getString(c.getColumnIndexOrThrow("transaction_date"));
                String status = c.getString(c.getColumnIndexOrThrow("transaction_status"));
                String delivery = c.getString(c.getColumnIndexOrThrow("transaction_delivery"));
                String buyerName = c.getString(c.getColumnIndexOrThrow("buyerName"));
                byte[] imageData = null;
                if (c.getColumnIndex("image") != -1) {
                    imageData = c.getBlob(c.getColumnIndexOrThrow("image"));
                }
                String itemName = c.getString(c.getColumnIndexOrThrow("itemName"));
                double itemPrice = c.getDouble(c.getColumnIndexOrThrow("price"));
                transactions.add(new Transaction(transactionId, itemId, sellerId, buyerId, date, status, delivery, buyerName, itemName, imageData, itemPrice));
            }
            c.close();
        }
        return transactions;
    }



}