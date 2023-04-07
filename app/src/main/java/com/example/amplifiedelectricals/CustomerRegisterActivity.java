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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CustomerRegisterActivity extends AppCompatActivity {

    EditText firstNameET, lastNameET, emailET, addressLine1ET, addressLine2ET, countyET, countryET, postcodeET, passwordET;
    TextView switchToLogin;
    Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        firstNameET = findViewById(R.id.firstName);
        lastNameET = findViewById(R.id.lastName);
        emailET = findViewById(R.id.email);
        addressLine1ET = findViewById(R.id.addressLine1);
        addressLine2ET = findViewById(R.id.addressLine2);
        countyET = findViewById(R.id.county);
        countryET = findViewById(R.id.country);
        postcodeET = findViewById(R.id.postcode);
        passwordET = findViewById(R.id.password);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String email = emailET.getText().toString();
                String addressLine1 = addressLine1ET.getText().toString();
                String addressLine2 = addressLine2ET.getText().toString();
                String county = countyET.getText().toString();
                String country = countryET.getText().toString();
                String postcode = postcodeET.getText().toString();
                String password = passwordET.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CustomerRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            HashMap<String, Object> customerHashmap = new HashMap<>();

                            customerHashmap.put("firstName", firstName);
                            customerHashmap.put("lastName", lastName);
                            customerHashmap.put("email", email);
                            customerHashmap.put("addressLine1", addressLine1);
                            customerHashmap.put("addressLine2", addressLine2);
                            customerHashmap.put("county", county);
                            customerHashmap.put("country", country);
                            customerHashmap.put("postcode", postcode);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            reference.setValue(customerHashmap);
                            reference.push();

                            Toast.makeText(CustomerRegisterActivity.this, "Registration Success!", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(CustomerRegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(CustomerRegisterActivity.this, "Registration failed, Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });





        switchToLogin = findViewById(R.id.switchToLogin);
        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}