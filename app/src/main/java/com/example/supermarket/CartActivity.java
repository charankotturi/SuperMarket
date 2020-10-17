package com.example.supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        initBNav();
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new FirstCartFragment());
        transaction.commit();


    }

    private void initBNav() {
        bottomNavigationView.setSelectedItemId(R.id.navBCart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navBSearch:
                        Intent intent = new Intent(CartActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navBHome:
                        Intent intent1 = new Intent(CartActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navBCart:
                        Toast.makeText(CartActivity.this ,"you are in Cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.BottomNavMenu);
        toolbar = findViewById(R.id.ToolBar);
    }

}
