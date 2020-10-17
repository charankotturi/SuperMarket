package com.example.supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import static com.example.supermarket.seeAllCategoriesFragment.CALLING_ACTIVITY;
import static com.example.supermarket.seeAllCategoriesFragment.GROCERY_CATEGORY;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Utils.initSharedPreferences(this);
        //Utils.clearSharedPreferences(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_start, R.string.drawer_end);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.categories:
                        seeAllCategoriesFragment dialog = new seeAllCategoriesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(GROCERY_CATEGORY, Utils.getCategories(MainActivity.this));
                        bundle.putString(CALLING_ACTIVITY, "main_activity");
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "debug see all categories dialog fragment.");
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new fragment_main());
        transaction.commit();
    }

    private void initView() {
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.ToolBar);
        navigationView = findViewById(R.id.navigationView);
    }

}