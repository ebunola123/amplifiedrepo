package com.example.amplifiedelectricals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerMainActivity extends AppCompatActivity {

    Button viewItem, viewCart, viewOrder, viewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String customerID = auth.getUid();

        viewItem = findViewById(R.id.viewItem);
        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainActivity.this, CustomerListItemsActivity.class);
                intent.putExtra("customerID", customerID);
                startActivity(intent);
            }
        });

        viewCart = findViewById(R.id.viewCart);
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainActivity.this, CustomerCartListActivity.class);
                intent.putExtra("customerID", customerID);
                startActivity(intent);
            }
        });

        viewOrder = findViewById(R.id.viewOrder);
        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainActivity.this, CustomerOrderActivity.class);
                intent.putExtra("customerID", customerID);
                startActivity(intent);
            }
        });


        viewProfile = findViewById(R.id.viewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainActivity.this, CustomerProfileActivity.class);
                intent.putExtra("customerID", customerID);
                startActivity(intent);
            }
        });

    }
}