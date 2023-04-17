package com.example.amplifiedelectricals.adminitems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.amplifiedelectricals.R;
import com.example.amplifiedelectricals.adminitemsearch.AdminSearchOptionsActivity;
import com.example.amplifiedelectricals.models.ModelItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ItemProfileActivity extends AppCompatActivity {

    EditText titleET, maufacturerET, priceET, categoryET;
    Button update;
    ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);

        Intent i = getIntent();
        String itemID = i.getStringExtra("itemID");

        titleET = findViewById(R.id.titleET);
        maufacturerET = findViewById(R.id.maufacturerET);
        priceET = findViewById(R.id.priceET);
        categoryET = findViewById(R.id.categoryET);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(itemID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelItems items = snapshot.getValue(ModelItems.class);

                titleET.setText(items.getTitle());
                maufacturerET.setText(items.getManufacturer());
                priceET.setText(items.getPrice());
                categoryET.setText(items.getCategory());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString();
                String manufacturer = maufacturerET.getText().toString();
                String price = priceET.getText().toString();
                String category = categoryET.getText().toString();

                HashMap<String, Object> editHashmap = new HashMap<>();

                editHashmap.put("title", title);
                editHashmap.put("manufacturer", manufacturer);
                editHashmap.put("category", category);
                editHashmap.put("price", price);
                editHashmap.put("itemID", itemID);

                reference.setValue(editHashmap);

            }
        });

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemProfileActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                reference.removeValue();

                Intent intent = new Intent(ItemProfileActivity.this, AdminSearchOptionsActivity.class);
                startActivity(intent);
            }
        });

    }
}