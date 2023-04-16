package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminItemSearchActivity extends AppCompatActivity {

    EditText searchET;
    ImageView searchButton;

    AdminCategorySearchAdapter categorySearchAdapter;
    AdminTitleSearchAdapter titleSearchAdapter;
    AdminManufacturerSearchAdapter manufacturerSearchAdapter;

    ArrayList<ModelItems> itemList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_item_search);

        Intent i = getIntent();
        String searchType = i.getStringExtra("searchType");

        searchButton = findViewById(R.id.searchButton);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();

        //method name
        readCategoryItems();

        searchET = findViewById(R.id.searchET);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchItems(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void searchItems(String s){
        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        String searchType = i.getStringExtra("searchType");
        Query query = FirebaseDatabase.getInstance().getReference("Items").orderByChild(searchType)
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelItems items = dataSnapshot.getValue(ModelItems.class);

                    assert items != null;
                    itemList.add(items);

                }
                categorySearchAdapter = new AdminCategorySearchAdapter(itemList, AdminItemSearchActivity.this);
                manufacturerSearchAdapter = new AdminManufacturerSearchAdapter(itemList, AdminItemSearchActivity.this);
                titleSearchAdapter = new AdminTitleSearchAdapter(itemList, AdminItemSearchActivity.this);

                if(searchType.equals("category")){
                    recyclerView.setAdapter(categorySearchAdapter);
                } else if(searchType.equals("title")){
                    recyclerView.setAdapter(titleSearchAdapter);
                } else if(searchType.equals("manufacturer")){
                    recyclerView.setAdapter(manufacturerSearchAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    };

    private void readCategoryItems(){
        Intent i = getIntent();
        String searchType = i.getStringExtra("searchType");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!searchET.getText().toString().equals("")){
                    itemList.clear();

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ModelItems items = dataSnapshot.getValue(ModelItems.class);
                        assert items != null;
                        itemList.add(items);
                    }
                    categorySearchAdapter = new AdminCategorySearchAdapter(itemList, AdminItemSearchActivity.this);
                    manufacturerSearchAdapter = new AdminManufacturerSearchAdapter(itemList, AdminItemSearchActivity.this);
                    titleSearchAdapter = new AdminTitleSearchAdapter(itemList, AdminItemSearchActivity.this);

                    if(searchType.equals("category")){
                        recyclerView.setAdapter(categorySearchAdapter);
                    } else if(searchType.equals("title")){
                        recyclerView.setAdapter(titleSearchAdapter);
                    } else if(searchType.equals("manufacturer")){
                        recyclerView.setAdapter(manufacturerSearchAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }












}