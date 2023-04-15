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

public class CustomerProfileActivity extends AppCompatActivity {

    TextView emailTV;
    EditText firstNameET, lastNameET, addressLine1ET, addressLine2ET, postcodeET, countyET, countryET, cardNameET, cardNumberET, expiryDateET, cvcET;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");

        emailTV = findViewById(R.id.emailTV);
        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        addressLine1ET = findViewById(R.id.addressLine1ET);
        addressLine2ET = findViewById(R.id.addressLine2ET);
        postcodeET = findViewById(R.id.postcodeET);
        countyET = findViewById(R.id.countyET);
        countryET = findViewById(R.id.countryET);
        cardNameET = findViewById(R.id.cardNameET);
        cardNumberET = findViewById(R.id.cardNumberET);
        expiryDateET = findViewById(R.id.expiryET);
        cvcET = findViewById(R.id.cvcET);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customers").child(customerID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCustomers customer = snapshot.getValue(ModelCustomers.class);

                emailTV.setText(customer.getEmail());
                firstNameET.setText(customer.getFirstName());
                lastNameET.setText(customer.getLastName());
                addressLine1ET.setText(customer.getAddressLine1());
                addressLine2ET.setText(customer.getAddressLine2());
                postcodeET.setText(customer.getPostcode());
                countyET.setText(customer.getCounty());
                countryET.setText(customer.getCountry());
                cardNameET.setText(customer.getCardName());
                cardNumberET.setText(customer.getCardNumber());
                expiryDateET.setText(customer.getExpiryDate());
                cvcET.setText(customer.getCvc());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String addressLine1 = addressLine1ET.getText().toString();
                String addressLine2 = addressLine2ET.getText().toString();
                String postcode = postcodeET.getText().toString();
                String county = countyET.getText().toString();
                String country = countryET.getText().toString();
                String cardName = cardNameET.getText().toString();
                String cardNumber = cardNumberET.getText().toString();

                if(cardNumber.charAt(0) != '4' || cardNumber.charAt(0) != '5'){
                    Toast.makeText(CustomerProfileActivity.this, "First Digit Should be 4 or 5", Toast.LENGTH_SHORT).show();
                    cardNumberET.requestFocus();
                }


                String expiryDate = expiryDateET.getText().toString();
                String cvc = cvcET.getText().toString();

                HashMap<String, Object> editHashmap = new HashMap<>();

                editHashmap.put("firstName", firstName);
                editHashmap.put("lastName", lastName);
                editHashmap.put("addressLine1", addressLine1);
                editHashmap.put("addressLine2", addressLine2);
                editHashmap.put("postcode", postcode);
                editHashmap.put("county", county);
                editHashmap.put("country", country);
                editHashmap.put("cardName", cardName);
                editHashmap.put("cardNumber", cardNumber);
                editHashmap.put("expiryDate", expiryDate);
                editHashmap.put("cvc", cvc);

                reference.setValue(editHashmap);

            }
        });







    }
}