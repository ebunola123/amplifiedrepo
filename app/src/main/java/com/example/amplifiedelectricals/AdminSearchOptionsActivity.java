package com.example.amplifiedelectricals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminSearchOptionsActivity extends AppCompatActivity {

    Button viewAll, searchTitle, searchCategory, searchManufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_options);

        viewAll = findViewById(R.id.viewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSearchOptionsActivity.this, AdminViewItemActivity.class);
                startActivity(intent);
            }
        });

        searchTitle = findViewById(R.id.searchTitle);
        searchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "title";
                Intent intent = new Intent(AdminSearchOptionsActivity.this, AdminItemSearchActivity.class);
                intent.putExtra("searchType", searchType);
                startActivity(intent);
            }
        });

        searchCategory = findViewById(R.id.searchCategory);
        searchCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "category";
                Intent intent = new Intent(AdminSearchOptionsActivity.this, AdminItemSearchActivity.class);
                intent.putExtra("searchType", searchType);
                startActivity(intent);
            }
        });

        searchManufacturer = findViewById(R.id.searchManufacturer);
        searchManufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "manufacturer";
                Intent intent = new Intent(AdminSearchOptionsActivity.this, AdminItemSearchActivity.class);
                intent.putExtra("searchType", searchType);
                startActivity(intent);
            }
        });



    }
}