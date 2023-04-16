package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerOrderDetailsActicity extends AppCompatActivity {

    CustomerOrderDetailsAdapter adapter;
    RecyclerView recyclerView;
    TextView fullPriceTV;
    ArrayList<ModelOrder> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_details_acticity);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        //String customerID = auth.getUid();

        Intent i = getIntent();
        String orderDate = i.getStringExtra("orderDate");
        String customerID = i.getStringExtra("customerID");
        System.out.println(orderDate);

        fullPriceTV = findViewById(R.id.fullPriceTV);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        adapter = new CustomerOrderDetailsAdapter(orderList, this);
        recyclerView.setAdapter(adapter);



        Query query = FirebaseDatabase.getInstance().getReference("Orders").child(customerID)
                .orderByChild("date")
                .equalTo(orderDate);

        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          //  orderList.clear();
            if(snapshot.exists()){
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelOrder order = dataSnapshot.getValue(ModelOrder.class);
                    orderList.add(order);
                    fullPriceTV.setText(order.getTotalPrice());
                }
            } else {
                Toast.makeText(CustomerOrderDetailsActicity.this, "No Order Information", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


}