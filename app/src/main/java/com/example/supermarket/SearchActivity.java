package com.example.supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.util.Util;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.supermarket.seeAllCategoriesFragment.CALLING_ACTIVITY;
import static com.example.supermarket.seeAllCategoriesFragment.GROCERY_CATEGORY;

public class SearchActivity extends AppCompatActivity implements seeAllCategoriesFragment.GET_ALL_CATEGORIES{

    private TextView editTxtSearch, firstSearchCategory, secondSearchCategory, thirdSearchCategory, txtSeeAllCategories;
    private ImageView btnSearchIcon;
    private RecyclerView recViewSearchBox;
    private BottomNavigationView sBottomNavMenu;
    private MaterialToolbar toolbar;
    private grocery_items_adaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initBNav();
        setSupportActionBar(toolbar);

        adaptor = new grocery_items_adaptor(this);
        recViewSearchBox.setAdapter(adaptor);
        recViewSearchBox.setLayoutManager(new GridLayoutManager(this,2));

        btnSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch();
            }
        });

        editTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final ArrayList<String> categories = Utils.getCategories(this);
        if(categories != null){
            if(categories.size() > 0) {
                if(categories.size() == 1){
                    searchCategory(categories, 1);
                }else if(categories.size() == 2){
                    searchCategory(categories, 2);
                }else if(categories.size() == 3){
                    searchCategory(categories, 3);
                }
            }
        }

        Intent intent = getIntent();
        if(intent != null){
            String itemsList = intent.getStringExtra("categories");
            ArrayList<groceryItems> itemsGList = Utils.getListCategoryItems(this, itemsList);
            if (itemsGList != null) {
                adaptor.setAllItems(itemsGList);
            }
        }

        txtSeeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeAllCategoriesFragment dialog = new seeAllCategoriesFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(GROCERY_CATEGORY, categories);
                bundle.putString(CALLING_ACTIVITY, "search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "debug see all categories dialog fragment.");
            }
        });

    }
    private void searchCategory(final ArrayList<String> category, int i){
        switch(i){
            case 1:
                firstSearchCategory.setVisibility(View.VISIBLE);
                firstSearchCategory.setText(category.get(0));
                secondSearchCategory.setVisibility(View.GONE);
                thirdSearchCategory.setVisibility(View.GONE);
                firstSearchCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<groceryItems> getItems = Utils.getListCategoryItems(SearchActivity.this, category.get(0));
                        if(getItems != null) {
                            adaptor.setAllItems(getItems);
                        }
                    }
                });

                break;
            case 2:
                firstSearchCategory.setVisibility(View.VISIBLE);
                firstSearchCategory.setText(category.get(0));
                secondSearchCategory.setVisibility(View.VISIBLE);
                secondSearchCategory.setText(category.get(1));
                thirdSearchCategory.setVisibility(View.GONE);
                firstSearchCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<groceryItems> getItems = Utils.getListCategoryItems(SearchActivity.this, category.get(0));
                        if(getItems != null) {
                            adaptor.setAllItems(getItems);
                        }
                    }
                });

                secondSearchCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<groceryItems> getItems = Utils.getListCategoryItems(SearchActivity.this, category.get(1));
                        if(getItems != null) {
                            adaptor.setAllItems(getItems);
                        }
                    }
                });

                break;
            case 3:
                firstSearchCategory.setVisibility(View.VISIBLE);
                firstSearchCategory.setText(category.get(0));
                secondSearchCategory.setVisibility(View.VISIBLE);
                secondSearchCategory.setText(category.get(1));
                thirdSearchCategory.setVisibility(View.VISIBLE);
                thirdSearchCategory.setText(category.get(2));
                firstSearchCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<groceryItems> getItems = Utils.getListCategoryItems(SearchActivity.this, category.get(0));
                        if(getItems != null) {
                            adaptor.setAllItems(getItems);
                        }
                    }
                });

                secondSearchCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<groceryItems> getItems = Utils.getListCategoryItems(SearchActivity.this, category.get(1));
                        if(getItems != null) {
                            adaptor.setAllItems(getItems);
                        }
                    }
                });

                thirdSearchCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<groceryItems> getItems = Utils.getListCategoryItems(SearchActivity.this, category.get(2));
                        if(getItems != null) {
                            adaptor.setAllItems(getItems);
                        }
                    }
                });
                break;

            default:
                firstSearchCategory.setVisibility(View.GONE);
                secondSearchCategory.setVisibility(View.GONE);
                thirdSearchCategory.setVisibility(View.GONE);
                break;
        }
    }

    private void initSearch(){
        if(!editTxtSearch.getText().toString().equals("")){
            String text = editTxtSearch.getText().toString();
            ArrayList<groceryItems> searchList = Utils.searchItem(this,text);
            if (searchList != null) {
                adaptor.setAllItems(searchList);
            }
        }
    }

    private void initBNav() {
        sBottomNavMenu.setSelectedItemId(R.id.navBSearch);
        sBottomNavMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navBSearch:
                        Toast.makeText(SearchActivity.this , "You have selected search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navBHome:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.navBCart:
                        Toast.makeText(SearchActivity.this , "you have selected Cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        editTxtSearch = findViewById(R.id.editTxtSearch);
        firstSearchCategory = findViewById(R.id.firstSearchCategory);
        secondSearchCategory = findViewById(R.id.secondSearchCategory);
        thirdSearchCategory = findViewById(R.id.thirdSearchCategory);
        txtSeeAllCategories = findViewById(R.id.txtSeeAllCategories);
        btnSearchIcon = findViewById(R.id.btnSearch);
        recViewSearchBox = findViewById(R.id.recViewSearchBox);
        sBottomNavMenu = findViewById(R.id.SBottomNavMenu);
        toolbar = findViewById(R.id.SToolBar);
    }

    @Override
    public void getCategories(String category) {
        ArrayList<groceryItems> items = Utils.getListCategoryItems(this, category);
        if(items != null) {
            adaptor.setAllItems(items);
        }
    }
}