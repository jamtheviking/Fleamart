package com.csis3175.fleamart.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;

import java.text.DecimalFormat;
import java.util.List;

//CardAdapter configuration for Recycler View (Grid view for search results)

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;


    //Default constructor for search
    public CardAdapter(){}
    public CardAdapter(Context context, List<Item> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    // JO
    public void updatedList(List<Item> updatedList) {
        itemList.clear(); // Clear the existing list
        itemList.addAll(updatedList); // Add all elements from the updated list

    }
    // JO





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_seachview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("#,###,###$");

        Item item = itemList.get(position);
        double itemPrice = item.getItemPrice();
        double discount = item.getDiscount();
        double discountedPrice = itemPrice * (1 - discount);

        holder.itemNameTextView.setText(item.getItemName());
        holder.itemPriceTextView.setText(df.format(discountedPrice));

//        holder.itemImageView.setImageDrawable(product.getImageDrawable());


        Glide.with(context)
                .load(item.getImageData()) // Load image from the imagePath string
                .into(holder.itemImageView);



        //Onclick listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemDisplay.class);
                intent.putExtra("item", item);
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemPriceTextView;
        ImageView itemImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemName);
            itemPriceTextView = itemView.findViewById(R.id.itemPrice);
            itemImageView = itemView.findViewById(R.id.img);
        }
    }

}