package com.example.amplifiedelectricals.adminmain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.amplifiedelectricals.R;
import com.example.amplifiedelectricals.models.ModelCustomers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminCustomerProfileActivity extends AppCompatActivity {

    TextView emailTV, firstNameTV, lastNameTV, addressLine1TV, addressLine2TV, postcodeTV, countyTV, countryTV;
    Button viewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customer_profile);

        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");

        emailTV = findViewById(R.id.emailTV);
        firstNameTV = findViewById(R.id.firstNameTV);
        lastNameTV = findViewById(R.id.lastNameTV);
        addressLine1TV = findViewById(R.id.addressLine1TV);
        addressLine2TV = findViewById(R.id.addressLine2TV);
        postcodeTV = findViewById(R.id.postcodeTV);
        countyTV = findViewById(R.id.countyTV);
        countryTV = findViewById(R.id.countryTV);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customers").child(customerID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCustomers customer = snapshot.getValue(ModelCustomers.class);

                emailTV.setText(customer.getEmail());
                firstNameTV.setText(customer.getFirstName());
                lastNameTV.setText(customer.getLastName());
                addressLine1TV.setText(customer.getAddressLine1());
                addressLine2TV.setText(customer.getAddressLine2());
                postcodeTV.setText(customer.getPostcode());
                countyTV.setText(customer.getCounty());
                countryTV.setText(customer.getCountry());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        viewOrders = findViewById(R.id.viewOrders);
        viewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCustomerProfileActivity.this, CustomerPurchaseHistoryActivity.class);
                intent.putExtra("customerID", customerID);
                startActivity(intent);
            }
        });

    }
}