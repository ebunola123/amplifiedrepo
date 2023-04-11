package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerCheckoutActivity extends AppCompatActivity {

    //ArrayList<ModelCart> cartList;
    EditText cardNameET, cardNoET, expiryDateET, cvcET, addressLine1ET, addressLine2ET, countyET, countryET;
    CustomerCartListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ModelCart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);
        
        //when checkout button is clicked, get the quantity and subtract it from the item ids in stock db


        //open stock db - get the item ID,
        //define orderITemID outside the scope
        //if orderItemID = stockItemID:
        //stockItemQuantity - orderQuantity
        //set stockItemQuanity to new value

        //put in a for loop depending on the list of item ids in cart



        //do a db quaery where itemID == orderItemID
        //if exists, do stockItemQuantity - orderQuantity




       // DatabaseReference reference =

        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();

        if(currentUser==null){
            Intent intent=new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
        }

        String customerID = auth.getUid();

        Intent i = getIntent();
        String total = i.getStringExtra("totalPrice");
        System.out.println("MY TOTAL - " + total);


        //payment details
        cardNameET = findViewById(R.id.cardNameET);
        cardNoET = findViewById(R.id.cardNoET);
        expiryDateET = findViewById(R.id.expiryDateET);
        cvcET = findViewById(R.id.cvcET);

        //deleivery address details
        addressLine1ET = findViewById(R.id.addressLine1ET);
        addressLine2ET = findViewById(R.id.addressLine2ET);
        countyET = findViewById(R.id.countyET);
        countryET = findViewById(R.id.countryET);



        //recycler view details
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        adapter = new CustomerCartListAdapter(cartList, this);
        recyclerView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart").child(customerID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelCart cart = dataSnapshot.getValue(ModelCart.class);
                    cartList.add(cart);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Customers").child(customerID);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCustomers customer = snapshot.getValue(ModelCustomers.class);

                addressLine1ET.setText(customer.getAddressLine1());
                addressLine2ET.setText(customer.getAddressLine2());
                countyET.setText(customer.getCounty());
                countryET.setText(customer.getCountry());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //open stock db
        //if cartlist.contains any of the values
        
        
    }
}