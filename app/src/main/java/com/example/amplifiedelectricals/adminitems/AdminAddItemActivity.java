package com.example.amplifiedelectricals.adminitems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amplifiedelectricals.customermain.CustomerLoginActivity;
import com.example.amplifiedelectricals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminAddItemActivity extends AppCompatActivity {

    EditText titleET, manET, priceET, categoryET;
    ImageView itemImage;
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
        itemImage = findViewById(R.id.itemImage);
        itemImage.setImageResource(R.drawable.headphones1);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");
                String itemID = reference.push().getKey();

                String title = titleET.getText().toString();
                String manufacturer = manET.getText().toString();
                String category = categoryET.getText().toString();
                String priceString = priceET.getText().toString();
                double price = Double.parseDouble(priceString);
                String image = itemImage.toString();

                HashMap<String, Object> itemHashmap = new HashMap<>();

                itemHashmap.put("title", title);
                itemHashmap.put("manufacturer", manufacturer);
                itemHashmap.put("category", category);
                itemHashmap.put("price", priceString);
                itemHashmap.put("itemID", itemID);
              //  itemHashmap.put("image", image);

                reference.child(itemID).setValue(itemHashmap);
                Toast.makeText(AdminAddItemActivity.this, "Item Saved", Toast.LENGTH_SHORT).show();

                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("StockLevel").child(itemID);
                HashMap<String, Object> stockHashmap = new HashMap<>();
                String stock = "0";
                stockHashmap.put("itemID", itemID);
                stockHashmap.put("stock", stock);
                reference2.setValue(stockHashmap);

               /* FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();

                storageReference.child(image);
                StorageReference reference1 = storageReference.child("itemImages/" + image);*/


               /* Storage storage = Storage.getInstance().;
                StorageReference storageRef = storage.getReference();*/


            }
        });



    }
}