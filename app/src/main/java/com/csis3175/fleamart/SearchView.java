package com.csis3175.fleamart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        Button btnSearch = findViewById(R.id.btnSearch); // JO
        EditText edtextSearch = findViewById(R.id.editTextSearch); // JO
        List<Product> productList = getCardData(); // JO






        RecyclerView rView = findViewById(R.id.recycler);
        rView.setLayoutManager(new GridLayoutManager(this, 2));
        CardAdapter cardAdapter = new CardAdapter(getCardData());
        rView.setAdapter(cardAdapter);

// JO
    btnSearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String q = edtextSearch.getText().toString();
            List<Product> updatedList = new ArrayList<>();
            Iterator<Product> iterate = productList.iterator();
            while (iterate.hasNext()) {
               Product p = iterate.next();
               if (p.getItemName().contains(q)) {
                   updatedList.add(p);
               }
            }
            cardAdapter.updatedList(updatedList);
        }
    });
    }
    // JO
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