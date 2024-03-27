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
    //private List<Item> itemList;
    private Context context;
    private User user;

    private Item item;

    public TransacationsAdapter(){    }

    @NonNull
    @Override
    public TransacationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_card_item, parent, false);
        return new TransacationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacationsAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactionsList.get(position);

        //Item item = findItemById(transaction.getItemId());
        //Glide.with(context).load(item.getImageData()).into(holder.itemImageView);
        holder.transactionStatus.setText(transaction.getStatus());
        //holder.itemNameTextView.setText(item.getItemName());
        holder.buyerId.setText(transaction.getBuyerId());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TransactionDetailsPage.class);
                //intent.putExtra("item", item);
                intent.putExtra("transaction", transaction);
                intent.putExtra("user", user);

                view.getContext().startActivity(intent);
            }
        });

    }

    /*private Item findItemById(int itemId) {
        for (Item item : itemList) {
            if (item.getItemID() == itemId) {
                return item;
            }
        }
        return null;
    }*/

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public TransacationsAdapter(Context context, List<Transaction> transactionsList, User user){
        this.context = context;
        this.transactionsList = transactionsList;
        this.user = user;
        //this.itemList = itemList;
    }

    public void updateTransactionsList(List<Transaction> transactionsList){
        transactionsList.clear();
        transactionsList.addAll(transactionsList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView transactionStatus;
        TextView buyerId;
        ImageView itemImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemName);
            transactionStatus = itemView.findViewById(R.id.itemPrice);
            buyerId = itemView.findViewById(R.id.txtBuyerID_Transactions);
            itemImageView = itemView.findViewById(R.id.img);
        }
    }

}
