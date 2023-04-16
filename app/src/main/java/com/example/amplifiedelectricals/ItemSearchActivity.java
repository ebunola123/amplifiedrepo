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
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemSearchActivity extends AppCompatActivity {

    EditText searchET;
    ImageButton searchButton;
    CategorySearchAdapter categorySearchAdapter;
    ArrayList<ModelItems> itemList;
    RecyclerView recyclerView;

/*
    Intent i = getIntent();
    String customerID;
    String searchType;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search);

        /*Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        String searchType = i.getStringExtra("searchType");*/
        Intent i2 = getIntent();
        String searchType = i2.getStringExtra("searchType");
        System.out.println(searchType);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        //method name
        readCategoryItems();



        //if searchType = category
        //then adapter = CategorySearchAdapter
        //create 3 adapters for each search type!! and set the recyclerview adapter to the correct one!



        //searchButton = findViewById(R.id.searchButton);
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
                categorySearchAdapter = new CategorySearchAdapter(itemList, ItemSearchActivity.this, customerID);
                recyclerView.setAdapter(categorySearchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    };

    private void readCategoryItems(){
        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
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
                    categorySearchAdapter = new CategorySearchAdapter(itemList, ItemSearchActivity.this, customerID);
                    recyclerView.setAdapter(categorySearchAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}