package com.example.supermarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class seeAllCategoriesFragment extends DialogFragment {

    public interface GET_ALL_CATEGORIES{
        void getCategories(String category);
    }

    private GET_ALL_CATEGORIES get_all_categories;

    public static final String GROCERY_CATEGORY = "all_categories";
    public static final String CALLING_ACTIVITY = "calling_activity";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.see_all_categories_card, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            final ArrayList<String> categories = bundle.getStringArrayList(GROCERY_CATEGORY);
            final String callingActivity = bundle.getString(CALLING_ACTIVITY);
            if(categories != null){
                ListView listview = view.findViewById(R.id.linLayoutAllCategories);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        categories);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch(callingActivity){
                            case "main_activity":
                                Intent intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("categories", categories.get(position));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getActivity().startActivity(intent);
                                break;
                            case "search_activity":
                                try {
                                    get_all_categories = (GET_ALL_CATEGORIES) getActivity();
                                    get_all_categories.getCategories(categories.get(position));
                                    dismiss();
                                }catch (ClassCastException e){
                                    e.printStackTrace();
                                    dismiss();
                                }

                        }
                    }
                });
            }
        }

        return builder.create();
    }
}
