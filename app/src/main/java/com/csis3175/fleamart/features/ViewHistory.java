package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.ItemAdapter;
import com.csis3175.fleamart.model.User;

import java.util.List;

public class ViewHistory extends AppCompatActivity {

    private RecyclerView itemsRecycler;
    private ItemAdapter adapter;


    User user;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        itemsRecycler = findViewById(R.id.recyclerView2);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper db = new DatabaseHelper(this);
        userId = user.getId();
        List<Item> items = db.viewAllItemsList(userId); // this is most likely an issue here. I used item instead of Cursor.
        adapter = new ItemAdapter(this, items); //
        itemsRecycler.setAdapter(adapter);

    }
}