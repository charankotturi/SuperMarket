package com.example.supermarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartAdaptor extends RecyclerView.Adapter<CartAdaptor.ViewHolder> {

    ArrayList<groceryItems> newItems = new ArrayList<>();

    public interface DeletingItem {
        void cartItem(groceryItems item);
    }

    private DeletingItem deletingItem;

    private Context context;
    private Fragment fragment;

    public CartAdaptor(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.CartItemName.setText(newItems.get(position).getName());
        holder.textCartItemRate.setText(String.valueOf(newItems.get(position).getPrice())+ " $");
        holder.btnCartItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Deleting...");
                builder.setMessage("Are you sure about the action");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            deletingItem = (DeletingItem) fragment;
                            deletingItem.cartItem(newItems.get(position));
                        }catch (ClassCastException e){
                            e.printStackTrace();
                        }
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newItems.size();
    }

    public void setNewItems(ArrayList<groceryItems> newItems) {
        this.newItems = newItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CartItemName, textCartItemRate;
        private Button btnCartItemDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CartItemName = itemView.findViewById(R.id.cartItemNameTxt);
            textCartItemRate = itemView.findViewById(R.id.CartItemRate);
            btnCartItemDelete = itemView.findViewById(R.id.btnDeleteCartItem);

        }
    }
}
