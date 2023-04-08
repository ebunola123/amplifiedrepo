package com.example.amplifiedelectricals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminAddItemActivity extends AppCompatActivity {

    EditText titleET, manET, priceET, categoryET;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_item);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        titleET = findViewById(R.id.titleET);
        manET = findViewById(R.id.manET);
        priceET = findViewById(R.id.priceET);
        categoryET = findViewById(R.id.categoryET);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");

                String title = titleET.getText().toString();
                String manufacturer = manET.getText().toString();
                String category = categoryET.getText().toString();
                String priceString = priceET.getText().toString();
                double price = Double.parseDouble(priceString);
                String itemID = reference.push().getKey();

                HashMap<String, Object> itemHashmap = new HashMap<>();

                itemHashmap.put("title", title);
                itemHashmap.put("manufacturer", manufacturer);
                itemHashmap.put("category", category);
                itemHashmap.put("price", price);
                itemHashmap.put("itemID", itemID);

                reference.child(itemID).setValue(itemHashmap);
                Toast.makeText(AdminAddItemActivity.this, "Item Saved", Toast.LENGTH_SHORT).show();


            }
        });



    }
}