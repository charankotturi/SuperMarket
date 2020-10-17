package com.example.supermarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Review_Adaptor extends RecyclerView.Adapter<Review_Adaptor.ViewHolder> {

    private ArrayList<Review> reviews = new ArrayList<>();

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtText.setText(reviews.get(position).getText());
        holder.txtUserName.setText(reviews.get(position).getUserName());
        holder.txtDate.setText(reviews.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtUserName, txtText, txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txtReviewUserName);
            txtText = itemView.findViewById(R.id.txtReviewText);
            txtDate = itemView.findViewById(R.id.txtReviewDate);

        }
    }
}
