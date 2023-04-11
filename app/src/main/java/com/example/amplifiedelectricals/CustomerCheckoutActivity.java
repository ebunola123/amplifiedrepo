package com.example.amplifiedelectricals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CustomerCheckoutActivity extends AppCompatActivity {

    //ArrayList<ModelCart> cartList;

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


        Intent i = getIntent();
        String total = i.getStringExtra("totalPrice");
        System.out.println("MY TOTAL - " + total);

       // DatabaseReference reference =

    }
}