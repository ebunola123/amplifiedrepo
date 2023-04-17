package com.example.amplifiedelectricals.customercheckout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.amplifiedelectricals.customermain.CustomerLoginActivity;
import com.example.amplifiedelectricals.customermain.CustomerMainActivity;
import com.example.amplifiedelectricals.models.ModelCart;
import com.example.amplifiedelectricals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerCartListActivity extends AppCompatActivity {

    CustomerCartListAdapter adapter;
    RecyclerView recyclerView;
    Button checkout;
    ImageButton deleteCart;
    ArrayList<ModelCart> cartList;
    int total2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart_list);

        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();

        if(currentUser==null){
            Intent intent=new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
        }

        String customerID = auth.getUid();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        adapter = new CustomerCartListAdapter(cartList, this);
        recyclerView.setAdapter(adapter);

        //String quantity;
        String tottalPrice;
       // int total2;

        int mytotal;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart").child(customerID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelCart cart = dataSnapshot.getValue(ModelCart.class);
                    String quantity = cart.getQuantity();
                    String price = cart.getPrice();

                    int total = Integer.parseInt(quantity) * Integer.parseInt(price);
                    System.out.println(total);

                    cartList.add(cart);
                     total2 = 0;
                    for(int i=0; i<=cartList.size(); i++){
                        total2 = total2 + total;
                    }

                    System.out.println("Total: €" + total2);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });



        checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String totalPrice = Integer.toString(total2);
                Intent intent = new Intent(CustomerCartListActivity.this, CustomerCheckoutActivity.class);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);

                Toast.makeText(CustomerCartListActivity.this, "Total Price: €" + totalPrice, Toast.LENGTH_SHORT).show();
            }
        });

        deleteCart = findViewById(R.id.deleteCart);
        deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerCartListActivity.this, "Cart Deleted", Toast.LENGTH_SHORT).show();
                reference.removeValue();

                Intent intent = new Intent(CustomerCartListActivity.this, CustomerMainActivity.class);
                startActivity(intent);
            }
        });


    }
}