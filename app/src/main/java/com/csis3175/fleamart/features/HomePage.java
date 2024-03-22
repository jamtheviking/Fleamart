package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.model.User;

public class HomePage extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");

            // Example: Display user details in TextViews or other UI elements
            TextView tvFullName = findViewById(R.id.tvFullName);
            tvFullName.setText(user.getFirstName() + " " + user.getLastName());
        }

        Button btnSell = findViewById(R.id.btnSell);
        Button btnBuy = findViewById(R.id.btnBuy);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnTransactions = findViewById(R.id.btnTransactions);
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, SellPage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent updateIntent = new Intent(HomePage.this, SearchView.class);
                updateIntent.putExtra("user", user);
                startActivity(updateIntent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(HomePage.this, UpdatePage.class);
                // Assuming 'user' holds the current user's data
                updateIntent.putExtra("user", user);
                startActivity(updateIntent);
            }
        });

        //Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(HomePage.this, ViewHistory.class);
                // Assuming 'user' holds the current user's data
                updateIntent.putExtra("user", user);
                startActivity(updateIntent);
            }
        });

    }
}