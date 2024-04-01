package com.csis3175.fleamart.features;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.*;
import com.csis3175.fleamart.model.CardAdapter;
import com.csis3175.fleamart.model.CustomEditText;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class  SearchView extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private int userId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        //initialize DB
        databaseHelper = new DatabaseHelper(SearchView.this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);




        //https://exchangetuts.com/setting-span-size-of-single-row-in-staggeredgridlayoutmanager-1639642992135800
        RecyclerView rView = findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        rView.setLayoutManager(gridLayoutManager);
        CardAdapter cardAdapter = new CardAdapter(SearchView.this,getCardData());

        rView.setAdapter(cardAdapter);

        CustomEditText searchBar = findViewById(R.id.search_bar);

        //Override Perform click with CustomEditText and configure OnTouch for right side of edit box and for Enter Key
        searchBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // drawableEndPosition = right side of edit text with Icon drawable
                int drawableEndPosition = searchBar.getRight() - searchBar.getCompoundDrawables()[2].getBounds().width();
                String searchTerm = Objects.requireNonNull(searchBar.getText()).toString().toLowerCase();
                if (event.getAction() == MotionEvent.ACTION_UP && event.getRawX() >= drawableEndPosition) {
                    searchItems(searchTerm, cardAdapter, rView);
                    return true;
                }
                return false;
            }
        });
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String searchTerm = Objects.requireNonNull(searchBar.getText()).toString().toLowerCase();
                    searchItems(searchTerm,cardAdapter,rView);
                    return true;
                }
                return false;
            }
        });
    }

    public void searchItems(String searchTerm, CardAdapter ca,RecyclerView rView){

        List<Item> allItems = new ArrayList<>(getCardData());
        List<Item> updatedList = new ArrayList<>();
        for(Item item : allItems){
            if(item.getStatus().equals("available")){
                if (item.getItemName() != null && item.getItemName().toLowerCase().contains(searchTerm)) {
                    updatedList.add(item);
                } else if (item.getLocation() != null && item.getLocation().toLowerCase().contains(searchTerm)) {
                    updatedList.add(item);
                } else if (item.getCategory() != null && item.getCategory().toLowerCase().contains(searchTerm)){
                    updatedList.add(item);
                } else if (item.getItemDescription() != null && item.getItemDescription().toLowerCase().contains(searchTerm)){
                    updatedList.add(item);
                } else if (item.getTag() != null && item.getTag().toLowerCase().contains(searchTerm)){
                    updatedList.add(item);
                }
            }

        }
        ca.updatedList(updatedList);
        rView.setAdapter(ca);

    }

    private List<Item> getCardData(){
        List<Item> items = new ArrayList<>();
        // Retrieve data from the database
        Cursor c = databaseHelper.viewAllItems(userId);
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
            items.add(new Item(itemId,name, description, price, isShareable,discount,dateString,imageData,userId,location,category, tag));
            }
        // Close the cursor
        c.close();
        return items;

    }
}