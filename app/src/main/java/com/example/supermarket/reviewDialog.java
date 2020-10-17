package com.example.supermarket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.supermarket.GroceryItemActivity.GROCERY_ITEMS;

public class reviewDialog extends DialogFragment {

    public interface AddReview{
        void addNewReviewItem(Review review);
    }

    private AddReview addReview;
    private TextView txtReview, txtUserName, txtItemName, txtWaring;
    private Button btnAdd;
    private groceryItems ReviewItem;
    public static final String TAG = "dialog item";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.review_dialog,null);
        initView(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            ReviewItem = bundle.getParcelable(GROCERY_ITEMS);
            if(ReviewItem != null){
                txtItemName.setText(ReviewItem.getName());
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = String.valueOf(txtUserName.getText());
                        String reviewText = String.valueOf(txtReview.getText());
                        String date = getTime();
                        if(userName.equals("") || reviewText.equals("")){
                            txtWaring.setText("enter valid fields!");
                            txtWaring.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onClick: wtf man enter the fields plz");
                        }
                        else{
                            txtWaring.setVisibility(View.GONE);
                            try {
                                addReview = (AddReview) getActivity();
                                addReview.addNewReviewItem(new Review(ReviewItem.getID(), userName, reviewText, date));
                                dismiss();
                            }catch (ClassCastException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }

        return builder.create();
    }

    private String getTime(){
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        return sdf.format(calender.getTime());
    }

    private void initView(View view) {
        txtReview = view.findViewById(R.id.txtReviewText);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtItemName = view.findViewById(R.id.txtRItemName);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtWaring = view.findViewById(R.id.txtWarning);
    }
}
