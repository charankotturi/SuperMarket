package com.example.supermarket;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.supermarket.GroceryItemActivity.GROCERY_ITEMS;

public class grocery_items_adaptor extends RecyclerView.Adapter<grocery_items_adaptor.ViewHolder> {

    private ArrayList<groceryItems> allItems = new ArrayList<>();
    private Context mContext;

    public grocery_items_adaptor(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemName.setText(allItems.get(position).getName());
        holder.itemPrice.setText("$ " +String.valueOf(allItems.get(position).getPrice()));
        Glide.with(mContext)
                .asBitmap()
                .load(allItems.get(position).getImageUrl())
                .into(holder.imgItem);

        holder.cardGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GroceryItemActivity.class);
                intent.putExtra(GROCERY_ITEMS, allItems.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }

    public void setAllItems(ArrayList<groceryItems> allItems) {
        this.allItems = allItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView itemName;
        private TextView itemPrice;
        private ImageView imgItem;
        private CardView cardGrocery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.txtItemName);
            itemPrice = itemView.findViewById(R.id.txtItemPrice);
            imgItem = itemView.findViewById(R.id.imgItem);
            cardGrocery = itemView.findViewById(R.id.cardGrocery);

        }
    }
}
