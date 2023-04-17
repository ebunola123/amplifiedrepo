package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class CustomerListItemsActivity extends AppCompatActivity {

    CustomerListItemsAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ModelItems> itemList;
    ImageButton sortButton, sortButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list_items);

        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();

        if(currentUser==null){
            Intent intent=new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
        }

        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        sortButton = findViewById(R.id.sortButton);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new CustomerListItemsAdapter(itemList, this, customerID);
        recyclerView.setAdapter(adapter);




        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelItems items = dataSnapshot.getValue(ModelItems.class);
                    itemList.add(items);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sortOptions = new String[]{"Ascending", "Descending"};
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerListItemsActivity.this);
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
                                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                                }
                            });
                            adapter.notifyDataSetChanged();

                        } else if (which == 1) {
                            //sort by descendin
                            Collections.reverse(itemList);
                            adapter.notifyDataSetChanged();
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