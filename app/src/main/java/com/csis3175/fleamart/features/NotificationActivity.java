package com.csis3175.fleamart.features;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Notifications;
import com.csis3175.fleamart.model.NotificationsCardAdapter;

import java.util.ArrayList;


public class NotificationActivity extends Fragment implements View.OnClickListener {
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    int userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(requireContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvNotifications = view.findViewById(R.id.rvNotifications);
        GridLayoutManager gm = new GridLayoutManager(requireContext(), 1);
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        rvNotifications.setLayoutManager(gm);
        NotificationsCardAdapter nca = new NotificationsCardAdapter(requireContext(), getNotifications());
        rvNotifications.setAdapter(nca);

    }

    private ArrayList<Notifications> getNotifications() {
        ArrayList<Notifications> notificationsList = new ArrayList<>();
        Cursor c = databaseHelper.viewNotifications(userId);
        if (c != null && c.moveToFirst()) {
            do {
                int notificationId = c.getInt(c.getColumnIndexOrThrow("notificationId"));
                String notificationMessage = c.getString(c.getColumnIndexOrThrow("notificationMessage"));
                int transaction_id = c.getInt(c.getColumnIndexOrThrow("transaction_id"));
                int transaction_buyer_id = c.getInt(c.getColumnIndexOrThrow("transaction_buyer_id"));
                int transaction_seller_id = c.getInt(c.getColumnIndexOrThrow("transaction_seller_id"));
                notificationsList.add(new Notifications(notificationId, notificationMessage, transaction_id, transaction_buyer_id, transaction_seller_id));
            } while (c.moveToNext());
            c.close(); // Close the cursor after use
        }
        return notificationsList;
    }

    @Override
    public void onClick(View view) {

    }
}