package com.csis3175.fleamart.features;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.User;

public class HomePage extends AppCompatActivity {

    private int userId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        DatabaseHelper db = new DatabaseHelper(HomePage.this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userid",0);

        if (userId>0){
            String[] userDetails = db.getUserDetails(userId);
            TextView tvFullName = findViewById(R.id.tvFullName);
            tvFullName.setText(String.format(userDetails[0]+" "+userDetails[1]));

        }


        //TODO remove this intent. Switch to sharedpreferences
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("user")) {
//            user = (User) intent.getSerializableExtra("user");
//
//            // Example: Display user details in TextViews or other UI elements
//            TextView tvFullName = findViewById(R.id.tvFullName);
//            tvFullName.setText(user.getFirstName() + " " + user.getLastName());
//        }

        Button btnPost = findViewById(R.id.btnSell);
        Button btnBuy = findViewById(R.id.btnBuy);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnEditItems = findViewById(R.id.btnEditPostedItems);
        Button btnTransactions = findViewById(R.id.btnTransactions);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, SellPage.class);
//                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        btnEditItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, EditItems.class);
//                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        btnTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, TransactionsPage.class);
//                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent updateIntent = new Intent(HomePage.this, SearchView.class);
//                updateIntent.putExtra("user", user);
                startActivity(updateIntent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(HomePage.this, UpdatePage.class);
                // Assuming 'user' holds the current user's data
//                updateIntent.putExtra("user", user);
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

    }
}