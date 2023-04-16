package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateStockActivity extends AppCompatActivity {

    TextView titleTV, manufacturerTV, categoryTV, currentStock;
    EditText stockET;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);

        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();

        if(currentUser==null){
            Intent intent=new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
        }

        Intent i = getIntent();
        String itemID = i.getStringExtra("itemID");

        titleTV = findViewById(R.id.titleTV);
        manufacturerTV = findViewById(R.id.manufacturerTV);
        categoryTV = findViewById(R.id.categoryTV);

        currentStock = findViewById(R.id.currentStock);
        stockET = findViewById(R.id.stockET);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(itemID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelItems items = snapshot.getValue(ModelItems.class);

                titleTV.setText(items.getTitle());
                manufacturerTV.setText(items.getManufacturer());
                categoryTV.setText(items.getCategory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("StockLevel").child(itemID);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelStock stockLevel = snapshot.getValue(ModelStock.class);
               // String test = String.valueOf(stockLevel.getStock());

                String stock1 = stockLevel.getStock();
                //int stock = Integer.parseInt(stock1);
                currentStock.setText(stock1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> stockHashmap = new HashMap<>();

                String stock = stockET.getText().toString();
                //String stock = Integer.parseInt(stock1);



                String oldInt = currentStock.getText().toString();
                int old = Integer.parseInt(oldInt);
                int newStock  = Integer.parseInt(stock);

                if (newStock <0){
                    Toast.makeText(UpdateStockActivity.this, "A Negavtive Amount Was Entered - Stock Adjusted Accordingly", Toast.LENGTH_LONG).show();
                }


                int updatedStock = old + newStock;
                String updated = Integer.toString(updatedStock);

                stockHashmap.put("itemID", itemID);
                stockHashmap.put("stock", updated);

                reference2.setValue(stockHashmap);
                Toast.makeText(UpdateStockActivity.this, "Stock Updated Accordingly", Toast.LENGTH_SHORT).show();



                currentStock.setText(updated);
            }
        });



    }
}