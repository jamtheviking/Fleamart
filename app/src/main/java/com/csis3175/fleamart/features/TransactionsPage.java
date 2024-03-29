package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.SellerCardAdapter;
import com.csis3175.fleamart.model.TransacationsAdapter;
import com.csis3175.fleamart.model.Transaction;
import com.csis3175.fleamart.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsPage extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    private int userId;
    SharedPreferences sharedPreferences;
    RecyclerView rvTransactionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);


        rvTransactionsView = findViewById(R.id.rvTransactionsView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        rvTransactionsView.setLayoutManager(gridLayoutManager);
        TransacationsAdapter transacationsAdapter = new TransacationsAdapter(TransactionsPage.this, getTransactions());
        rvTransactionsView.setAdapter(transacationsAdapter);
    }

    private ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Log.d("TRANSTEST","user id is "+transactions.size());

        Cursor c = databaseHelper.viewUserTransactions(userId);
        while (c.moveToNext()) {
            int transactionId = c.getInt(c.getColumnIndexOrThrow("transaction_id"));
            int buyerId = c.getInt(c.getColumnIndexOrThrow("transaction_buyer_id"));
            int sellerId = c.getInt(c.getColumnIndexOrThrow("transaction_seller_id"));
            int itemId = c.getInt(c.getColumnIndexOrThrow("itemid"));
            String date = c.getString(c.getColumnIndexOrThrow("transaction_date"));
            String status = c.getString(c.getColumnIndexOrThrow("transaction_status"));
            String delivery = c.getString(c.getColumnIndexOrThrow("transaction_delivery"));
            String buyerName = c.getString(c.getColumnIndexOrThrow("buyerName"));
            byte[] imageData = c.getBlob(c.getColumnIndexOrThrow("image"));
            String itemName = c.getString(c.getColumnIndexOrThrow("itemName"));
            double itemPrice = c.getDouble(c.getColumnIndexOrThrow("price"));
            transactions.add(new Transaction(transactionId, itemId, sellerId, buyerId, date, status, delivery,buyerName,itemName,imageData,itemPrice));
        }

        // Close the cursor
        c.close();
        return transactions;
    }

//    private ArrayList<Item> getPostedItemsData(){
//        ArrayList<Item> postedItems = new ArrayList<>();
//
//        Cursor c = databaseHelper.viewPostedItemsByUser(userId);
//        while (c.moveToNext()) {
//            int itemId = c.getInt(c.getColumnIndexOrThrow("itemid"));
//            String name = c.getString(c.getColumnIndexOrThrow("name"));
//            String description = c.getString(c.getColumnIndexOrThrow("description"));
//            double price = c.getDouble(c.getColumnIndexOrThrow("price"));
//            double discount = c.getDouble(c.getColumnIndexOrThrow("discount"));
//            boolean isShareable = c.getInt(c.getColumnIndexOrThrow("isShareable"))==1;
//            String dateString = c.getString(c.getColumnIndexOrThrow("date"));
//            byte[] imageData = c.getBlob(c.getColumnIndexOrThrow("image"));
//            int userId = c.getInt(c.getColumnIndexOrThrow("posterid"));
//            String location = c.getString(c.getColumnIndexOrThrow("location"));
//            String category = c.getString(c.getColumnIndexOrThrow("category"));
//            String tag = c.getString(c.getColumnIndexOrThrow("tag"));
//            postedItems.add(new Item(itemId,name, description, price, isShareable,discount,dateString,imageData,userId,location,category, tag));
//        }
//        Log.d("TRANSTEST","posted count is "+postedItems.size());
//        // Close the cursor
//        c.close();
//        return postedItems;
//    }

}