package com.csis3175.fleamart.model;

import android.content.Context;
import android.content.Intent;
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
import com.csis3175.fleamart.features.TransactionDetailsPage;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {


    private List<Transaction> transactionsList;
    private Context context;

    TextView buyer_text;
    TextView seller_text;
    private Item item;

    int userId;

    int itemid;

    private int newItemCount = 0;
    SharedPreferences sharedPreferences;


    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_card_item, parent, false);
        return new OrderHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
        DatabaseHelper db = new DatabaseHelper(context);


        Transaction transaction = transactionsList.get(position);



        String name;

        if (userId == transaction.getSellerId()){
            //Returns the name of the buyer on the Sales View when the current user is the seller
            name = "Buyer: " + db.getUsernameByID(transaction.getBuyerId());
        } else {
            //Returns the name of the seller on the Orders View when the current user is the buyer
            name = "Seller: " + db.getUsernameByID(transaction.getSellerId());
        }
        Glide.with(context)
                .load(transaction.getImageData())
                .into(holder.itemImageView);
        holder.transactionStatus.setText(transaction.getStatus());
        holder.itemNameTextView.setText(transaction.getItemName());
        holder.buyerName.setText(name);

        sharedPreferences = holder.itemView.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        /**
         * This part is commented out in order history
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
        return transactionsList.size();
    }

    public OrderHistoryAdapter(Context context, List<Transaction> transactionsList){
        this.context = context;
        this.transactionsList = transactionsList;
    }


    /**
     * Not being used at the moment
     * @param buyertext
     * @param sellertext
     * @param status
     * @param userId
     */
    public void checkUserStatus(TextView buyertext, TextView sellertext, String status, int userId) {
        Transaction transactions = new Transaction();
        if(userId == transactions.getSellerId() && "Finalize".equals(status)) {
            // Item is finalized, show seller and hide buyer
            buyertext.setVisibility(View.VISIBLE);
            sellertext.setVisibility(View.INVISIBLE);
        } else {
            // For other statuses, show buyer and hide seller (or adjust as needed)
            sellertext.setVisibility(View.INVISIBLE);
            buyertext.setVisibility(View.VISIBLE);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView transactionStatus;
        TextView buyerName;
        ImageView itemImageView;

        TextView buyer_text;
        TextView seller_text;
        ImageView newItemAlert;


        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.txtItemName_Transactions);
            transactionStatus = itemView.findViewById(R.id.txtStatus_Transactions);
            buyerName = itemView.findViewById(R.id.txtBuyerID_Transactions);
            itemImageView = itemView.findViewById(R.id.imageView_Transactions);
            buyer_text = itemView.findViewById(R.id.textBuyer);
            seller_text = itemView.findViewById(R.id.txtSeller);
            newItemAlert = itemView.findViewById(R.id.newItem);
        }
    }

}
