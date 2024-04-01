package com.csis3175.fleamart.features;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;

public class HomePage extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DatabaseHelper db = new DatabaseHelper(HomePage.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button btnPost = findViewById(R.id.btnSell);
        Button btnBuy = findViewById(R.id.btnBuy);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnEditItems = findViewById(R.id.btnEditPostedItems);
        Button btnTransactions = findViewById(R.id.btnTransactions);
        Button btnViewOrderStatus = findViewById(R.id.btnStatusOrder);

        ImageView notification = findViewById(R.id.notification);


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);

        if (userId>0){
            String[] userDetails = db.getUserDetails(userId);
            TextView tvFullName = findViewById(R.id.tvFullName);
            tvFullName.setText(String.format(userDetails[0]+" "+userDetails[1]));
        }
        boolean isFinalized = db.isTransactionFinalized(userId);
        if (isFinalized) {
            notification.setVisibility(View.VISIBLE); // Show notification
        }

        btnViewOrderStatus.setOnClickListener(view -> startActivity(new Intent(HomePage.this, viewOrderStatus.class)));

        // notification.setOnClickListener(view -> startActivity(new Intent(HomePage.this, TransactionsPage.class)));

        /* POST ITEM ACTIVITY
         */
        btnPost.setOnClickListener(view -> startActivity(new Intent(HomePage.this, SellPage.class)));

        /* EDIT ITEM ACTIVITY
         */
        btnEditItems.setOnClickListener(view -> startActivity(new Intent(HomePage.this, EditItems.class)));

        /* TRANSACTION HISTORY ACTIVITY
         */
        btnTransactions.setOnClickListener(view -> startActivity(new Intent(HomePage.this, TransactionsPage.class)));

        /* BUY ITEM > SEARCH VIEW ACTIVITY
         */
        btnBuy.setOnClickListener(v -> startActivity(new Intent(HomePage.this, SearchView.class)));

        /* UPDATE PAGE ACTIVITY
         */
        btnUpdate.setOnClickListener(v -> startActivity(new Intent(HomePage.this, UpdatePage.class)));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(HomePage.this, "GOODBYE", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(HomePage.this, LandingPage.class));
            }
        });


    }
//public void clickNotification(View view) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//    NotificationFragment nf = new NotificationFragment();
//    ft.add(R.id.fragmentViewer,nf);
//    ft.commit();
}

