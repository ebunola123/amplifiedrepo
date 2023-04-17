package com.example.amplifiedelectricals.customeritemsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.amplifiedelectricals.R;

public class CustomerSearchOptionsActivity extends AppCompatActivity {

    Button viewAll, searchTitle, searchCategory, searchManufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);

        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");

        viewAll = findViewById(R.id.viewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerSearchOptionsActivity.this, CustomerListItemsActivity.class);
                intent.putExtra("customerID", customerID);
                startActivity(intent);
            }
        });

        searchTitle = findViewById(R.id.searchTitle);
        searchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "title";
                Intent intent = new Intent(CustomerSearchOptionsActivity.this, ItemSearchActivity.class);
                intent.putExtra("customerID", customerID);
                intent.putExtra("searchType", searchType);
                startActivity(intent);
            }
        });

        searchCategory = findViewById(R.id.searchCategory);
        searchCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "category";
                Intent intent = new Intent(CustomerSearchOptionsActivity.this, ItemSearchActivity.class);
                intent.putExtra("customerID", customerID);
                intent.putExtra("searchType", searchType);
                startActivity(intent);
            }
        });

        searchManufacturer = findViewById(R.id.searchManufacturer);
        searchManufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "manufacturer";
                Intent intent = new Intent(CustomerSearchOptionsActivity.this, ItemSearchActivity.class);
                intent.putExtra("customerID", customerID);
                intent.putExtra("searchType", searchType);
                startActivity(intent);
            }
        });

    }
}