package com.example.amplifiedelectricals.customermain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.amplifiedelectricals.customerorders.CustomerOrderActivity;
import com.example.amplifiedelectricals.customeritemsearch.CustomerSearchOptionsActivity;
import com.example.amplifiedelectricals.R;
import com.example.amplifiedelectricals.customercheckout.CustomerCartListActivity;
import com.example.amplifiedelectricals.models.ModelCustomers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomerMainActivity extends AppCompatActivity {

    Button viewItem, viewCart, viewOrder, viewProfile;
    TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        hello = findViewById(R.id.hello);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customers").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCustomers customers = snapshot.getValue(ModelCustomers.class);
                hello.setText("Hello " + customers.getFirstName() + " " + customers.getLastName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

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
                Intent intent = new Intent(CustomerMainActivity.this, CustomerSearchOptionsActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //login page
        if (id == R.id.logoutItem) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}