package com.csis3175.fleamart.model;

import android.content.Context;
import android.content.Intent;
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
import com.csis3175.fleamart.features.EditItemPage;
import com.csis3175.fleamart.features.TransactionDetailsPage;

import java.util.List;

public class TransacationsAdapter extends RecyclerView.Adapter<TransacationsAdapter.ViewHolder> {

    private List<Transaction> transactionsList;
//    private List<Item> itemList;
    private Context context;


    private Item item;
    int itemid;



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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TransactionDetailsPage.class);
                intent.putExtra("item", item);
                intent.putExtra("transaction", transaction);
                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public TransacationsAdapter(Context context, List<Transaction> transactionsList){
        this.context = context;
        this.transactionsList = transactionsList;
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

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.txtItemName_Transactions);
            transactionStatus = itemView.findViewById(R.id.txtStatus_Transactions);
            buyerId = itemView.findViewById(R.id.txtBuyerID_Transactions);
            itemImageView = itemView.findViewById(R.id.imageView_Transactions);
        }
    }

}
