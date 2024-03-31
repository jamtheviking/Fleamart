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

public class TransacationsAdapter extends RecyclerView.Adapter<TransacationsAdapter.ViewHolder> {

    private List<Transaction> transactionsList;
//    private List<Item> itemList;
    private Context context;

    TextView buyer_text;
    TextView seller_text;
    private Item item;

    int itemid;

    private int newItemCount = 0;
SharedPreferences sharedPreferences;


    @NonNull
    @Override
    public TransacationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_card_item, parent, false);
        return new TransacationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacationsAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactionsList.get(position);
        String buyerName = transaction.getBuyerName();
//        Item item = findItemById(transaction.getItemId());
        Glide.with(context)
                .load(transaction.getImageData())
                .into(holder.itemImageView);
        holder.transactionStatus.setText(transaction.getStatus());
        holder.itemNameTextView.setText(transaction.getItemName());
        holder.buyerId.setText(buyerName);




        sharedPreferences = holder.itemView.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        Transaction transaction1 = new Transaction(); // code here and below checks 1) buyer/seller status and what to display
        String checkStatus = transaction1.getStatus(); //2) checks if an item card has been clicked already
        checkUserStatus(holder.buyer_text, holder.seller_text, checkStatus, userId);

        if (transaction.getNewTransaction()) {
            holder.newItemAlert.setVisibility(View.VISIBLE);
            newItemCount++;
        } else {
            holder.newItemAlert.setVisibility(View.INVISIBLE);
        }

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

    }
public int getNewItemCount() {
        return newItemCount;
}


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public TransacationsAdapter(Context context, List<Transaction> transactionsList){
        this.context = context;
        this.transactionsList = transactionsList;
    }



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



//    public void updateTransactionsList(List<Transaction> transactionsList){
//        transactionsList.clear();
//        transactionsList.addAll(transactionsList);
//
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView transactionStatus;
        TextView buyerId;
        ImageView itemImageView;

        TextView buyer_text;
        TextView seller_text;
        ImageView newItemAlert;


        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.txtItemName_Transactions);
            transactionStatus = itemView.findViewById(R.id.txtStatus_Transactions);
            buyerId = itemView.findViewById(R.id.txtBuyerID_Transactions);
            itemImageView = itemView.findViewById(R.id.imageView_Transactions);
            buyer_text = itemView.findViewById(R.id.textBuyer);
            seller_text = itemView.findViewById(R.id.txtSeller);
            newItemAlert = itemView.findViewById(R.id.newItem);
        }
    }

}
