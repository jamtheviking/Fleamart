package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.SellerCardAdapter;
import com.csis3175.fleamart.model.User;

import java.util.ArrayList;
import java.util.List;

public class EditItems extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private User user;
    private int userId;
    SharedPreferences sharedPreferences;
    RecyclerView rvTransactionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);

        databaseHelper = new DatabaseHelper(this);

        rvTransactionsView = findViewById(R.id.rvTransactionsView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        rvTransactionsView.setLayoutManager(gridLayoutManager);
        SellerCardAdapter sellerCardAdapter = new SellerCardAdapter(EditItems.this,getPostedItemsData());

        rvTransactionsView.setAdapter(sellerCardAdapter);
    }

    private List<Item> getPostedItemsData(){
        List<Item> postedItems = new ArrayList<>();

        Cursor c = databaseHelper.viewPostedItemsByUser(userId);
        while (c.moveToNext()) {
            int itemId = c.getInt(c.getColumnIndexOrThrow("itemid"));
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            String description = c.getString(c.getColumnIndexOrThrow("description"));
            double price = c.getDouble(c.getColumnIndexOrThrow("price"));
            double discount = c.getDouble(c.getColumnIndexOrThrow("discount"));
            boolean isShareable = c.getInt(c.getColumnIndexOrThrow("isShareable"))==1;
            String dateString = c.getString(c.getColumnIndexOrThrow("date"));
            byte[] imageData = c.getBlob(c.getColumnIndexOrThrow("image"));
            int userId = c.getInt(c.getColumnIndexOrThrow("posterid"));
            String location = c.getString(c.getColumnIndexOrThrow("location"));
            String category = c.getString(c.getColumnIndexOrThrow("category"));
            String tag = c.getString(c.getColumnIndexOrThrow("tag"));
            postedItems.add(new Item(itemId,name, description, price, isShareable,discount,dateString,imageData,userId,location,category, tag));
        }
        // Close the cursor
        c.close();
        return postedItems;
    }
}