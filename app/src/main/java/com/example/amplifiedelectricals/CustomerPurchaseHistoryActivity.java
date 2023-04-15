package com.example.amplifiedelectricals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CustomerPurchaseHistoryActivity extends AppCompatActivity {

    CustomerOrderAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ModelOrder> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_purchase_history);



    }
}