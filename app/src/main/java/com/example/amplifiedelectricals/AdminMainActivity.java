package com.example.amplifiedelectricals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMainActivity extends AppCompatActivity {

    Button addItem, viewItem, updateStock, viewCustomer;
    TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        addItem = findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminAddItemActivity.class);
                startActivity(intent);
            }
        });

    }
}