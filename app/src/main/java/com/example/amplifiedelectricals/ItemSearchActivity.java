package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ItemSearchActivity extends AppCompatActivity {

    EditText searchET;
    CategorySearchAdapter categorySearchAdapter;
    TitleSearchAdapter titleSearchAdapter;
    ManufacturerSearchAdapter manufacturerSearchAdapter;
    ArrayList<ModelItems> itemList;
    RecyclerView recyclerView;

    ImageButton sortButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search);

       Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        /* String searchType = i.getStringExtra("searchType");*/
        Intent i2 = getIntent();
        String searchType = i2.getStringExtra("searchType");
        System.out.println(searchType);

        //searchButton = findViewById(R.id.searchButton);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();


        //method name
        readCategoryItems();





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
                   // items.getTitle().compareToIgnoreCase(s);

                    assert items != null;
                    itemList.add(items);

                }
                categorySearchAdapter = new CategorySearchAdapter(itemList, ItemSearchActivity.this, customerID);
                manufacturerSearchAdapter = new ManufacturerSearchAdapter(itemList, ItemSearchActivity.this, customerID);
                titleSearchAdapter = new TitleSearchAdapter(itemList, ItemSearchActivity.this, customerID);

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

    private void readCategoryItems() {
        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        String searchType = i.getStringExtra("searchType");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!searchET.getText().toString().equals("")) {
                    itemList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ModelItems items = dataSnapshot.getValue(ModelItems.class);
                        assert items != null;
                        itemList.add(items);
                    }
                    categorySearchAdapter = new CategorySearchAdapter(itemList, ItemSearchActivity.this, customerID);
                    manufacturerSearchAdapter = new ManufacturerSearchAdapter(itemList, ItemSearchActivity.this, customerID);
                    titleSearchAdapter = new TitleSearchAdapter(itemList, ItemSearchActivity.this, customerID);

                    if (searchType.equals("category")) {
                        recyclerView.setAdapter(categorySearchAdapter);
                    } else if (searchType.equals("title")) {
                        recyclerView.setAdapter(titleSearchAdapter);
                    } else if (searchType.equals("manufacturer")) {
                        recyclerView.setAdapter(manufacturerSearchAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sortOptions = new String[]{"Ascending", "Descending"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemSearchActivity.this);
                builder.setTitle("Sort By")
                        .setIcon(R.drawable.ic_baseline_sort_24);
                builder.setSingleChoiceItems(sortOptions, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //sort by ascending
                            Collections.sort(itemList, new Comparator<ModelItems>() {
                                @Override
                                public int compare(ModelItems o1, ModelItems o2) {
                                    if (searchType.equals("category")) {
                                        return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                                    } else if (searchType.equals("title")) {
                                        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                                    } else if (searchType.equals("manufacturer")) {
                                        return o1.getManufacturer().compareToIgnoreCase(o2.getManufacturer());
                                    } else {
                                        return o1.getTitle().compareTo(o2.getTitle());
                                    }

                                }
                            });

                            if (searchType.equals("category")) {
                                categorySearchAdapter.notifyDataSetChanged();
                            } else if (searchType.equals("title")) {
                                titleSearchAdapter.notifyDataSetChanged();
                            } else if (searchType.equals("manufacturer")) {
                                manufacturerSearchAdapter.notifyDataSetChanged();
                            }

                        } else if (which == 1) {
                            //sort by descendin
                            Collections.reverse(itemList);

                            if (searchType.equals("category")) {
                                categorySearchAdapter.notifyDataSetChanged();
                            } else if (searchType.equals("title")) {
                                titleSearchAdapter.notifyDataSetChanged();
                            } else if (searchType.equals("manufacturer")) {
                                manufacturerSearchAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
                //AlertDialog mDialog = builder.create();
                //mDialog.show();
                builder.show();
            }
        });

    }



}