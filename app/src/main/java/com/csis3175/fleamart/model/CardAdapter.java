package com.csis3175.fleamart.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175.fleamart.R;

import java.text.DecimalFormat;
import java.util.List;

//CardAdapter configuration for Recycler View (Grid view for search results)

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Product> productList;


    public CardAdapter(List<Product> productList) {
        this.productList = productList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("#,###,###$");
        Product product = productList.get(position);
        holder.itemNameTextView.setText(product.getItemName());
        holder.itemPriceTextView.setText(df.format(product.getItemPrice()));
        holder.itemImageView.setImageResource(product.getImgId());

        //Onclick listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemDisplay.class);
                intent.putExtra("itemName", product.getItemName());
                intent.putExtra("itemPrice", product.getItemPrice());
                intent.putExtra("itemDesc", product.getItemDescription());
                intent.putExtra("imgId", product.getImgId());
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
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
