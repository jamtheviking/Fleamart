package com.csis3175.fleamart.features;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.CardAdapter;
import com.csis3175.fleamart.model.CustomEditText;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class  SearchView extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private User user;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            userId = user.getId();
        }

        //initialize DB
        databaseHelper = new DatabaseHelper(this);

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
        CardAdapter cardAdapter = new CardAdapter(SearchView.this,getCardData(),user);

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
            if (item.getItemName().toLowerCase().contains(searchTerm)) {
                updatedList.add(item);
            }
        }
        ca.updatedList(updatedList);
        rView.setAdapter(ca);

    }

    private List<Item> getCardData(){
        List<Item> items = new ArrayList<>();
//        products.add(new Product("Clock",56,R.drawable.alarm_clock));
//        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
//        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
//        products.add(new Product("Dryer Machine",200,R.drawable.dryer));
//        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
//        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
//        products.add(new Product("Dryer Machine",12000,R.drawable.dryer));
//        products.add(new Product("Clock",56,R.drawable.alarm_clock));
//        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
//        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
//        products.add(new Product("Dryer Machine",200,R.drawable.dryer));
//        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
//        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
//        products.add(new Product("Dryer Machine",12000,R.drawable.dryer));

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
            items.add(new Item(itemId,name, description, price, isShareable,discount,dateString,imageData,userId,location,category));
            }
        // Close the cursor
        c.close();
        return items;

    }
}