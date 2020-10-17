package com.example.supermarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment implements CartAdaptor.DeletingItem {
    private TextView CartWarningText , CartEmptyText, cartText1, totalRateTxt;
    private RecyclerView CartItemsRecView;
    private Button btnFirstToSecondFragment;
    private CartAdaptor adapter;
    private RelativeLayout cartText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cart_first, container, false);
        initView(view);

        adapter = new CartAdaptor(getActivity(), this);

        CartItemsRecView.setAdapter(adapter);
        CartItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ArrayList<groceryItems> newItems = Utils.getCartList(getActivity());
        if(newItems != null){
            if(newItems.size() > 0) {
                adapter.setNewItems(newItems);
                CartEmptyText.setVisibility(View.GONE);
                cartText.setVisibility(View.VISIBLE);
                CartWarningText.setVisibility(View.GONE);
            }
            else{
                CartEmptyText.setVisibility(View.VISIBLE);
                cartText.setVisibility(View.GONE);
                CartWarningText.setVisibility(View.GONE);
            }
        }else{
            CartEmptyText.setVisibility(View.VISIBLE);
            cartText.setVisibility(View.GONE);
            CartWarningText.setVisibility(View.GONE);
        }

        btnFirstToSecondFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newItems != null){
                    if(newItems.size() < 0 ) {
                        CartEmptyText.setVisibility(View.VISIBLE);
                        cartText.setVisibility(View.GONE);
                        CartWarningText.setVisibility(View.VISIBLE);
                    }else if(newItems != null){
                        System.out.println("hey dipshit no problem here!");
                        FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.frameLayout, new SecondCartFragment());
                        transaction1.commit();
                    }
                }else{
                    CartEmptyText.setVisibility(View.VISIBLE);
                    cartText.setVisibility(View.GONE);
                    CartWarningText.setVisibility(View.VISIBLE);
                }
            }
        });

        initRate(newItems);

        return view;
    }

    private void initView(View view){
        CartEmptyText = view.findViewById(R.id.emptyCartText);
        CartWarningText = view.findViewById(R.id.emptyCartWarningTxt);
        btnFirstToSecondFragment = view.findViewById(R.id.btnFirstToSecondFragment);
        CartItemsRecView = view.findViewById(R.id.cartRecView);
        cartText = view.findViewById(R.id.textCart);
        cartText1 = view.findViewById(R.id.textCart1);
        totalRateTxt = view.findViewById(R.id.totalRateTxt);
    }

    @Override
    public void cartItem(groceryItems item) {
        Utils.deleteCartItem(getActivity(), item.getID());
        final ArrayList<groceryItems> newItems = Utils.getCartList(getActivity());
        System.out.println("shit wtf is happening " + newItems);
        if(newItems != null){
            if(newItems.size() > 0) {
                adapter.setNewItems(newItems);
                CartEmptyText.setVisibility(View.GONE);
                cartText.setVisibility(View.VISIBLE);
                CartWarningText.setVisibility(View.GONE);
            }
            else{
                CartEmptyText.setVisibility(View.VISIBLE);
                cartText.setVisibility(View.GONE);
                CartWarningText.setVisibility(View.GONE);
            }
        }else{
            CartEmptyText.setVisibility(View.VISIBLE);
            cartText.setVisibility(View.GONE);
            CartWarningText.setVisibility(View.GONE);
        }

        btnFirstToSecondFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newItems.size() == 0) {
                    CartEmptyText.setVisibility(View.VISIBLE);
                    cartText.setVisibility(View.GONE);
                    CartWarningText.setVisibility(View.VISIBLE);
                }else if(newItems != null){
                    System.out.println("hey dipshit no problem here!");
                    FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.frameLayout, new SecondCartFragment());
                    transaction1.commit();
                }
            }
        });

        initRate(newItems);

    }

    private void initRate(ArrayList<groceryItems> items){
        if (items != null){
            String cRate = "Total cost is $:";
            double rate = 0;
            for(groceryItems i: items){
                rate = rate + i.getPrice();
            }

            Math.round(rate*100/100);
            System.out.println(rate);
            totalRateTxt.setText(cRate +" "+String.valueOf(rate));

        }
    }
}
