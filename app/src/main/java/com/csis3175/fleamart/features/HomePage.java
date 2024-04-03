package com.csis3175.fleamart.features;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Encrypt;
import com.csis3175.fleamart.model.Notifications;

import java.io.Console;
import java.util.List;

public class HomePage extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DatabaseHelper db = new DatabaseHelper(HomePage.this);

    ImageButton btnNotification;
    private boolean notificationsVisible = false;
    FragmentContainerView notifsContainer;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button btnPost = findViewById(R.id.btnSell);
        Button btnBuy = findViewById(R.id.btnBuy);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnEditItems = findViewById(R.id.btnEditPostedItems);
        Button btnTransactions = findViewById(R.id.btnViewOrderHistory);
        Button btnViewOrderStatus = findViewById(R.id.btnStatusOrder);
        btnNotification = findViewById(R.id.btnNotification);
        notifsContainer = findViewById(R.id.fragmentContainterNotifications);
        notifsContainer.setVisibility(View.INVISIBLE);


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);


        updateNotificationVisibility();

        if (userId > 0) {
            String[] userDetails = db.getUserDetails(userId);
            TextView tvFullName = findViewById(R.id.tvFullName);
            tvFullName.setText(String.format(userDetails[0] + " " + userDetails[1]));
        }


        // notification.setOnClickListener(view -> startActivity(new Intent(HomePage.this, TransactionsPage.class)));

        /* POST ITEM ACTIVITY
         */
        btnPost.setOnClickListener(view -> startActivity(new Intent(HomePage.this, SellPage.class)));

        /* EDIT ITEM ACTIVITY
         */
        btnEditItems.setOnClickListener(view -> startActivity(new Intent(HomePage.this, EditItems.class)));

        /* TRANSACTION HISTORY ACTIVITY
         */
        //btnTransactions.setOnClickListener(view -> startActivity(new Intent(HomePage.this, TransactionsPage.class)));
        //btnViewOrderStatus.setOnClickListener(view -> startActivity(new Intent(HomePage.this, ViewOrderHistoryActivity.class)));
        btnViewOrderStatus.setOnClickListener(view -> startActivity(new Intent(HomePage.this, TransactionsPage.class)));
        btnTransactions.setOnClickListener(view -> startActivity(new Intent(HomePage.this, ViewOrderHistoryActivity.class)));
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

    @Override
    public void onResume(){
        super.onResume();
        updateNotificationVisibility();
    }

    private void updateNotificationVisibility() {
        int newNotifications = db.getNewNotificationsCount(userId);

        if (newNotifications > 0) {
            btnNotification.setVisibility(View.VISIBLE);
        } else {
            btnNotification.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This is the method to open and close Notifications
     * Notifications screen is a Fragment
     * @param view
     */
    public void btnNotification(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment notifs = fm.findFragmentById(R.id.fragmentContainterNotifications);



        // Check if the fragment is already visible
        if (notifs != null) {
            if (notificationsVisible) {
                // If fragment is visible, hide it
                notifsContainer.setVisibility(View.INVISIBLE);
                notificationsVisible = false;
                System.out.println("Fragment Hidden");
            } else {
                // If fragment is not visible, show it
                notifsContainer.setVisibility(View.VISIBLE);
                notificationsVisible = true;
                System.out.println("Fragment DISPLAYED");
            }
        } else {
            // Fragment not added yet, add it
            NotificationActivity na = new NotificationActivity();

            ft.add(R.id.fragmentContainterNotifications, na);
            notificationsVisible = true;
            System.out.println("Fragment DISPLAYED");
        }

        ft.commit();
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        Toast.makeText(this, "Press LOGOUT to exit", Toast.LENGTH_SHORT).show();

    }

}
