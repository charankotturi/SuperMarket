package com.example.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class fragment_main extends Fragment {

    private static final String TAG = "System compare";
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recViewNewItems, recViewPopularItems, recViewSuggestedItems;
    private grocery_items_adaptor newItemAdaptor, suggestedItemAdaptor, popularItemAdaptor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);

        initView(view);
        initBNav();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecView();
    }

    private void initRecView() {

        newItemAdaptor = new grocery_items_adaptor(getActivity());
        recViewNewItems.setAdapter(newItemAdaptor);
        recViewNewItems.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemAdaptor = new grocery_items_adaptor(getActivity());
        recViewPopularItems.setAdapter(popularItemAdaptor);
        recViewPopularItems.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemAdaptor = new grocery_items_adaptor(getActivity());
        recViewSuggestedItems.setAdapter(suggestedItemAdaptor);
        recViewSuggestedItems.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<groceryItems> newItems = Utils.getAllItems(getActivity());
        if(null != newItems) {
            Comparator<groceryItems> newItemCompare = new Comparator<groceryItems>() {
                @Override
                public int compare(groceryItems o1, groceryItems o2) {
//                    System.out.println(o1.getID());
                    return o1.getID() - o2.getID();
                }
            };
            Collections.sort(newItems, Collections.reverseOrder(newItemCompare));
            newItemAdaptor.setAllItems(newItems);
        }

        ArrayList<groceryItems> popularItems = Utils.getAllItems(getActivity());
        if(null != popularItems) {
            Comparator<groceryItems> popularItemsCompare = new Comparator<groceryItems>() {
                @Override
                public int compare(groceryItems o1, groceryItems o2) {
                    Log.d(TAG, "compare: " + o1.getPopularityPoints() + " " + o2.getPopularityPoints());
                    return o1.getPopularityPoints() - o2.getPopularityPoints();
                }
            };
            Collections.sort(popularItems, Collections.reverseOrder(popularItemsCompare));
            popularItemAdaptor.setAllItems(popularItems);
        }

        ArrayList<groceryItems> suggestedItems = Utils.getAllItems(getActivity());
        if(null != suggestedItems) {
            Comparator<groceryItems> suggestedItemsCompare = new Comparator<groceryItems>() {
                @Override
                public int compare(groceryItems o1, groceryItems o2) {
                    return o1.getUserPoints() - o2.getUserPoints();
                }
            };
            Collections.sort(suggestedItems, Collections.reverseOrder(suggestedItemsCompare));
            suggestedItemAdaptor.setAllItems(suggestedItems);
        }

    }

    private void initBNav() {
        bottomNavigationView.setSelectedItemId(R.id.navBHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navBSearch:
                        Intent intent = new Intent(getContext(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navBHome:
                        Toast.makeText(getActivity(), "you have selected home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navBCart:
                        Intent cartIntent = new Intent(getActivity(), CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        Toast.makeText(getActivity(), "you have selected Cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initView(View view) {
        bottomNavigationView = view.findViewById(R.id.BottomNavMenu);
        recViewNewItems = view.findViewById(R.id.recViewNewItems);
        recViewPopularItems = view.findViewById(R.id.recViewPopularItems);
        recViewSuggestedItems = view.findViewById(R.id.recViewSuggestedItems);
    }

}
