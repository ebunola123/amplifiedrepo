package com.example.amplifiedelectricals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AfterOrderActivity extends AppCompatActivity {

    String orderItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_order);

        //open order db

        //open stock db
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("StockLevel");
    }
}