package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.ItemAdapter;
import com.csis3175.fleamart.model.User;

import java.util.List;

public class HistoryTransactions extends AppCompatActivity {


    User user = new User();
    int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_transactions);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            userId = user.getId();
        }
            DatabaseHelper db = new DatabaseHelper(this);
            RecyclerView recyclerView = findViewById(R.id.recycler_view_items);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<Item> items = db.getUserListedItems(userId);
            ItemAdapter adapter = new ItemAdapter(this, items, user);
            recyclerView.setAdapter(adapter);

        }
    }
