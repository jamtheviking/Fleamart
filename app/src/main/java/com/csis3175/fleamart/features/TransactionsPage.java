package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.TransacationsAdapter;
import com.csis3175.fleamart.model.Transaction;

import java.util.ArrayList;

public class TransactionsPage extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    TextView buyerOrSeller;
    private int userId;
    SharedPreferences sharedPreferences;
    RecyclerView rvTransactionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);


        rvTransactionsView = findViewById(R.id.rvTransactionsView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        rvTransactionsView.setLayoutManager(gridLayoutManager);
        TransacationsAdapter transacationsAdapter = new TransacationsAdapter(TransactionsPage.this, getAllUserTransactions());

        rvTransactionsView.setAdapter(transacationsAdapter);


    }


    private ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Log.d("TRANSTEST", "user id is " + transactions.size());

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
            transactions.add(new Transaction(transactionId, itemId, sellerId, buyerId, date, status, delivery, buyerName, itemName, imageData, itemPrice));
        }

        // Close the cursor
        c.close();
        return transactions;
    }


//    private ArrayList<Transaction> getBuyerTransactions() {
//        ArrayList<Transaction> transactions = new ArrayList<>();
//        ArrayList<Transaction> buyer_transactions = new ArrayList<>();
//
//
//        Log.d("TRANSTEST", "user id is " + transactions.size());
//
//        Cursor c = databaseHelper.viewBuyersTransactions(userId);
//        while (c.moveToNext()) {
//            int transactionId = c.getInt(c.getColumnIndexOrThrow("transaction_id"));
//            int buyerId = c.getInt(c.getColumnIndexOrThrow("transaction_buyer_id"));
//            int sellerId = c.getInt(c.getColumnIndexOrThrow("transaction_seller_id"));
//            int itemId = c.getInt(c.getColumnIndexOrThrow("itemid"));
//            String date = c.getString(c.getColumnIndexOrThrow("transaction_date"));
//            String status = c.getString(c.getColumnIndexOrThrow("transaction_status"));
//            String delivery = c.getString(c.getColumnIndexOrThrow("transaction_delivery"));
//            String buyerName = c.getString(c.getColumnIndexOrThrow("buyerName"));
//            //byte[] imageData = c.getBlob(c.getColumnIndexOrThrow("image"));
//            String itemName = c.getString(c.getColumnIndexOrThrow("itemName"));
//            double itemPrice = c.getDouble(c.getColumnIndexOrThrow("price"));
//            transactions.add(new Transaction(transactionId, itemId, sellerId, buyerId, date, status, delivery, buyerName, itemName, null, itemPrice));
//        }
//
//        // Close the cursor
//        c.close();
//        return transactions;
//    }
private ArrayList<Transaction> getAllUserTransactions() {
    ArrayList<Transaction> allTransactions = new ArrayList<>();

    // Fetch and add buyer transactions
    Cursor cBuyer = databaseHelper.viewBuyersTransactions(userId);
    allTransactions.addAll(processTransactionCursor(cBuyer));

    // Fetch and add seller transactions
    Cursor cSeller = databaseHelper.viewUserTransactions(userId);
    allTransactions.addAll(processTransactionCursor(cSeller));

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