package com.csis3175.fleamart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            User user = (User) intent.getSerializableExtra("user");

            // Example: Display user details in TextViews or other UI elements
            TextView welcomeTextView = findViewById(R.id.tvFullName);
            welcomeTextView.setText("Welcome, " + user.getFirstName() + " " + user.getLastName());
        }

        Button btnSell = findViewById(R.id.btnSell);
        Button btnBuy = findViewById(R.id.btnBuy);
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Sell.class);
                startActivity(intent);
            }
        });

    }
}