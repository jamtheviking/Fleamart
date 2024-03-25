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
import com.csis3175.fleamart.features.FullDetailsItems;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;

    User user;

    public ItemAdapter(Context context, List<Item> itemList, User user) {
        this.context = context;
        this.itemList = itemList;
        this.user = user;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
    Item item = itemList.get(position);
    holder.textItemName.setText(item.getItemName());
    holder.textItemDescription.setText(item.getItemDescription());
    holder.textItemPrice.setText(String.valueOf(item.getItemPrice()));


   Glide.with(context)
     .load(item.getImageData())
       .placeholder(R.drawable.supernintendo)
           .into(holder.imageItem);

   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
      int position = holder.getAdapterPosition();
      if (position !=RecyclerView.NO_POSITION) {
          Item selectedItem = itemList.get(position);
          Intent intent = new Intent(context, FullDetailsItems.class);
          intent.putExtra("user", user);
          intent.putExtra("selectedItem", selectedItem);
          context.startActivity(intent);
      }
       }
   });
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textItemName, textItemDescription, textItemPrice;
      ImageView imageItem;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textItemName = itemView.findViewById(R.id.text_item_name);
            textItemDescription = itemView.findViewById(R.id.text_item_description);
            textItemPrice = itemView.findViewById(R.id.text_item_price);
            imageItem = itemView.findViewById(R.id.itemImg);

        }
    }
}
