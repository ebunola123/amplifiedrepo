package com.example.amplifiedelectricals.adminmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.amplifiedelectricals.R;
import com.example.amplifiedelectricals.adminitems.AdminAddItemActivity;
import com.example.amplifiedelectricals.adminitemsearch.AdminSearchOptionsActivity;
import com.example.amplifiedelectricals.customermain.CustomerLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMainActivity extends AppCompatActivity {

    Button addItem, viewItem, updateStock, viewCustomer;

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

        viewItem = findViewById(R.id.viewItem);
        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminSearchOptionsActivity.class);
                startActivity(intent);
            }
        });

        updateStock = findViewById(R.id.updateStock);
        updateStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, UpdateStockListActivity.class);
                startActivity(intent);
            }
        });

        viewCustomer = findViewById(R.id.viewCustomer);
        viewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, CustomerListActivity.class);
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