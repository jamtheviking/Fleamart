package com.csis3175.fleamart.features;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175.fleamart.model.CardAdapter;
import com.csis3175.fleamart.model.Product;
import com.csis3175.fleamart.R;

import java.util.ArrayList;
import java.util.List;

public class SearchView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        RecyclerView rView = findViewById(R.id.recycler);
        rView.setLayoutManager(new GridLayoutManager(this, 2));
        CardAdapter cardAdapter = new CardAdapter(getCardData());
        rView.setAdapter(cardAdapter);

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
        products.add(new Product("Clock",56,R.drawable.alarm_clock));
        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
        products.add(new Product("Dryer Machine",200,R.drawable.dryer));
        products.add(new Product("Coffee Maker",25,R.drawable.coffee1));
        products.add(new Product("Coffee Maker2",20,R.drawable.coffee2));
        products.add(new Product("Dryer Machine",12000,R.drawable.dryer));


        return products;
    }
}