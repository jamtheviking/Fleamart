package com.csis3175.fleamart;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class SearchView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        RecyclerView rView = findViewById(R.id.recycler);
        rView.setLayoutManager(new GridLayoutManager(this, 2));
        CardAdapter cardAdapter = new CardAdapter(getCardData());
        rView.setAdapter(cardAdapter);
        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnItemSelectedListener(this);
        Toolbar topmenu = findViewById(R.id.top_menu);
        setSupportActionBar(topmenu);

        Menu menu = topmenu.getMenu();
        getMenuInflater().inflate(R.menu.top_menu, menu);
        topmenu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.top_menu) {
                    // Handle search action
                    return true;
                }
                // Handle other menu item clicks if needed
                return false;
            }
        });


    }

    private List<Product> getCardData(){
        List<Product> products = new ArrayList<>();
        products.add(new Product("Clock",56,R.drawable.alarm_clock));
        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
        products.add(new Product("Dryer Machine",200,R.drawable.dryer));
        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
        products.add(new Product("Dryer Machine",12000,R.drawable.dryer));


        return products;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.search) {
            // Handle search menu item click
            // Example: startActivity(new Intent(SearchView.this, SearchActivity.class));
            return true;
        } else if (item.getItemId() == R.id.category) {
            // Handle profile menu item click
            // Example: startActivity(new Intent(SearchView.this, ProfileActivity.class));
            return true;
        }
        return false;
    }
}