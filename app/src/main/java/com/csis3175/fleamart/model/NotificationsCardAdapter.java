package com.csis3175.fleamart.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.features.TransactionDetailsPage;

import java.util.List;

public class NotificationsCardAdapter extends RecyclerView.Adapter<NotificationsCardAdapter.ViewHolder> {


    private List<Notifications> notificationsList;
    private Context context;
    int userId;
    SharedPreferences sharedPreferences;
    @NonNull
    @Override
    public NotificationsCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_card_list, parent, false);

        return new NotificationsCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);


        int reverse = notificationsList.size() - 1 - position;

        Notifications notifications = notificationsList.get(reverse);

        holder.tvNotificationMessage.setText(notifications.getNotificationMessage());




        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.updateNotificationStatus(notifications.getNotificationId(), true);
                Toast.makeText(context, "Notification is marked as read", Toast.LENGTH_SHORT).show();
            }


        });

    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public NotificationsCardAdapter(Context context, List<Notifications> notifications){
        this.context = context;
        this.notificationsList = notifications;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotificationMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNotificationMessage = itemView.findViewById(R.id.tvNotificationMessage);
        }
    }

}
