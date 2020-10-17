package com.example.supermarket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements reviewDialog.AddReview{

    public static final String TAG = "Grocery Items";
    public static final String GROCERY_ITEMS = "getting items";

    private Toolbar  toolbar;
    private TextView txtName, txtPrice, txtDescription, txtReview, txtToolBarText;
    private ImageView imgFirstStar, imgSecondStar, imgThirdStar, imgFirstEmptyStar, imgSecondEmptyStar, imgThirdEmptyStar,
                      imgItemPicture;
    private Button btnAddToCart;
    private RelativeLayout rel1, rel2, rel3;
    private RecyclerView reviewsRecView;
    private groceryItems incomingItem;
    private Review_Adaptor adaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        setSupportActionBar(toolbar);
        initView();

        adaptor = new Review_Adaptor();

        Intent intent = getIntent();
        if(intent != null) {
            incomingItem = intent.getParcelableExtra(GROCERY_ITEMS);
            if(incomingItem != null) {
                txtName.setText(incomingItem.getName());
                txtDescription.setText(incomingItem.getDescription());
                txtPrice.setText("$ "+String.valueOf(incomingItem.getPrice()));
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(imgItemPicture);

                txtToolBarText.setText(incomingItem.getCategory());

                ArrayList<Review> reviews = Utils.getReviews(this,incomingItem.getID());
                reviewsRecView.setAdapter(adaptor);
                reviewsRecView.setLayoutManager(new LinearLayoutManager(this));

                if(reviews != null){
                    if(reviews.size() > 0){
                        adaptor.setReviews(reviews);
                    }
                }

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.addItemToCart(GroceryItemActivity.this ,incomingItem);
                        Intent intent = new Intent(GroceryItemActivity.this, CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

                txtReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reviewDialog dialog = new reviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEMS, incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "Add review...");
                    }
                });

                handleRating();

            }
        }

    }

    private void handleRating() {

        switch (incomingItem.getRating()){
            case 0:
                imgFirstStar.setVisibility(View.INVISIBLE);
                imgFirstEmptyStar.setVisibility(View.VISIBLE);
                imgSecondStar.setVisibility(View.INVISIBLE);
                imgSecondEmptyStar.setVisibility(View.VISIBLE);
                imgThirdStar.setVisibility(View.INVISIBLE);
                imgThirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 1:
                imgFirstStar.setVisibility(View.VISIBLE);
                imgFirstEmptyStar.setVisibility(View.INVISIBLE);
                imgSecondStar.setVisibility(View.INVISIBLE);
                imgSecondEmptyStar.setVisibility(View.VISIBLE);
                imgThirdStar.setVisibility(View.INVISIBLE);
                imgThirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 2:
                imgFirstStar.setVisibility(View.VISIBLE);
                imgFirstEmptyStar.setVisibility(View.INVISIBLE);
                imgSecondStar.setVisibility(View.VISIBLE);
                imgSecondEmptyStar.setVisibility(View.INVISIBLE);
                imgThirdStar.setVisibility(View.INVISIBLE);
                imgThirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 3:
                imgFirstStar.setVisibility(View.VISIBLE);
                imgFirstEmptyStar.setVisibility(View.INVISIBLE);
                imgSecondStar.setVisibility(View.VISIBLE);
                imgSecondEmptyStar.setVisibility(View.INVISIBLE);
                imgThirdStar.setVisibility(View.VISIBLE);
                imgThirdEmptyStar.setVisibility(View.INVISIBLE);
                break;
            default:
                break;

        }
        rel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRating() != 1) {
                    Utils.changeRating(GroceryItemActivity.this, incomingItem.getID(), 1);
                    incomingItem.setRating(1);
                    handleRating();
                }else if(incomingItem.getRating() == 1){
                    Utils.changeRating(GroceryItemActivity.this, incomingItem.getID(), 0);
                    incomingItem.setRating(0);
                    handleRating();
                }
            }
        });

        rel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRating() != 2) {
                    Utils.changeRating(GroceryItemActivity.this, incomingItem.getID(), 2);
                    incomingItem.setRating(2);
                    handleRating();
                }else if(incomingItem.getRating() == 2){
                    Utils.changeRating(GroceryItemActivity.this, incomingItem.getID(), 0);
                    incomingItem.setRating(0);
                    handleRating();
                }
            }
        });

        rel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingItem.getRating() != 3) {
                    Utils.changeRating(GroceryItemActivity.this, incomingItem.getID(), 3);
                    incomingItem.setRating(3);
                    handleRating();
                }else if(incomingItem.getRating() == 3){
                    Utils.changeRating(GroceryItemActivity.this, incomingItem.getID(), 0);
                    incomingItem.setRating(0);
                    handleRating();
                }
            }
        });
    }

    private void initView() {
        txtToolBarText = findViewById(R.id.toolBarCategory);
        toolbar = findViewById(R.id.GToolBar);
        txtDescription = findViewById(R.id.txtGDescription);
        txtName = findViewById(R.id.txtGItemName);
        txtPrice = findViewById(R.id.txtGItemPrice);
        txtReview = findViewById(R.id.txtAddItemReview);
        btnAddToCart = findViewById(R.id.btnGAddToCart);
        imgItemPicture = findViewById(R.id.imgItemPicture);
        imgFirstEmptyStar = findViewById(R.id.imgFirstEmptyStar);
        imgFirstStar = findViewById(R.id.imgFirstFilledStar);
        imgSecondEmptyStar = findViewById(R.id.imgSecondEmptyStar);
        imgSecondStar = findViewById(R.id.imgSecondFilledStar);
        imgThirdEmptyStar = findViewById(R.id.imgThirdEmptyStar);
        imgThirdStar = findViewById(R.id.imgThirdFilledStar);
        reviewsRecView = findViewById(R.id.recViewReviews);
        rel1 = findViewById(R.id.one);
        rel2 = findViewById(R.id.two);
        rel3 = findViewById(R.id.three);

    }

    @Override
    public void addNewReviewItem(Review review) {
        Log.d(TAG, "addNewReviewItem: review dialog..");
        Utils.addReview(this,review);
        reviewsRecView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecView.setAdapter(adaptor);
        ArrayList<Review> allReviews = Utils.getReviews(this, incomingItem.getID());
        if (allReviews != null){
            adaptor.setReviews(allReviews);
        }
    }
}