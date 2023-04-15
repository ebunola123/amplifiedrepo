package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class CustomerItemActivity extends AppCompatActivity {

    TextView titleTV, manufacturerTV, priceTV, categoryTV, quantityTV;
    Button addToCart;
    ImageButton less, more;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_item);

        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        String itemID = i.getStringExtra("itemID");

        titleTV = findViewById(R.id.titleTV);
        manufacturerTV = findViewById(R.id.manufacturerTV);
        priceTV = findViewById(R.id.priceTV);
        categoryTV = findViewById(R.id.categoryTV);
        quantityTV = findViewById(R.id.quantityTV);

        addToCart = findViewById(R.id.addToCart);

        less = findViewById(R.id.less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count <= 0){
                    count = 0;
                }else {
                    count--;
                }

                String amount = Integer.toString(count);
                quantityTV.setText(amount);
            }
        });


        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                String amount = Integer.toString(count);
                quantityTV.setText(amount);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(itemID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelItems items = snapshot.getValue(ModelItems.class);

                titleTV.setText(items.getTitle());
                manufacturerTV.setText(items.getManufacturer());
                priceTV.setText(items.getPrice());
                categoryTV.setText(items.getCategory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count <= 0){
                    Toast.makeText(CustomerItemActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Cart").child(customerID);

                    String title = titleTV.getText().toString();
                    String price = priceTV.getText().toString();
                    String manufacturer = manufacturerTV.getText().toString();
                    String amount = Integer.toString(count);

                    HashMap<String, Object> cartHashmap = new HashMap<>();

                    cartHashmap.put("title", title);
                    cartHashmap.put("price", price);
                    cartHashmap.put("manufacturer", manufacturer);
                    cartHashmap.put("itemID", itemID);
                    cartHashmap.put("quantity", amount);

                    ref.child(itemID).setValue(cartHashmap);
                    Toast.makeText(CustomerItemActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
                

            }
        });


    }
}