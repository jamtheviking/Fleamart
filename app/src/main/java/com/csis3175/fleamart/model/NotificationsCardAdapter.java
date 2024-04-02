package com.csis3175.fleamart.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;

import java.util.List;

public class NotificationsCardAdapter extends RecyclerView.Adapter<NotificationsCardAdapter.ViewHolder> {


    private List<Notifications> notificationsList;
    private Context context;
    int userId;
    private int newItemCount = 0;
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
        //DatabaseHelper db = new DatabaseHelper(context); commented out for future use


        Notifications notifications = notificationsList.get(position);

        holder.tvNotificationMessage.setText(notifications.getNotificationMessage());


        /**
         * Can implement delete notification upon click
         *
         *
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transaction.getNewTransaction()) {
                    transaction.setNewTransaction(false);
                    newItemCount--;
                    notifyDataSetChanged();
                }
                Intent intent = new Intent(view.getContext(), TransactionDetailsPage.class);
                intent.putExtra("item", item);
                intent.putExtra("transaction", transaction);
                view.getContext().startActivity(intent);
            }
        });
         */
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
