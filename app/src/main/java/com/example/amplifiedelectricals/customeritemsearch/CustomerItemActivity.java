package com.example.amplifiedelectricals.customeritemsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amplifiedelectricals.customermain.CustomerLoginActivity;
import com.example.amplifiedelectricals.models.ModelComments;
import com.example.amplifiedelectricals.models.ModelCustomers;
import com.example.amplifiedelectricals.models.ModelItems;
import com.example.amplifiedelectricals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CustomerItemActivity extends AppCompatActivity {

    TextView titleTV, manufacturerTV, priceTV, categoryTV, quantityTV;
    Button addToCart;
    ImageButton less, more, addComment, rateButton;
    RecyclerView recyclerView;
    int count = 0;

    String comment;
    String firstName;

    ItemCommentsAdapter adapter;
    ArrayList<ModelComments> commentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_item);

        //when writing comment, open alert dialog and add star rating there!

        //open stock db with the itemid - if stock is less than 0 or if stock is less than count, then give toast message


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String email = currentUser.getEmail();

        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        String itemID = i.getStringExtra("itemID");


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentList = new ArrayList<>();
        adapter = new ItemCommentsAdapter(commentList, this);
        recyclerView.setAdapter(adapter);


       //
        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("Comments").child(itemID);
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelComments comments = dataSnapshot.getValue(ModelComments.class);
                    commentList.add(comments);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        titleTV = findViewById(R.id.titleTV);
        manufacturerTV = findViewById(R.id.manufacturerTV);
        priceTV = findViewById(R.id.priceTV);
        categoryTV = findViewById(R.id.categoryTV);
        quantityTV = findViewById(R.id.quantityTV);

        addToCart = findViewById(R.id.addToCart);

        less = findViewById(R.id.less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count <= 0){
                    count = 0;
                }else {
                    count--;
                }

                String amount = Integer.toString(count);
                quantityTV.setText(amount);
            }
        });


        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                String amount = Integer.toString(count);
                quantityTV.setText(amount);
            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Customers").child(customerID);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCustomers customers = snapshot.getValue(ModelCustomers.class);
                firstName = customers.getFirstName();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(itemID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelItems items = snapshot.getValue(ModelItems.class);

                titleTV.setText(items.getTitle());
                manufacturerTV.setText(items.getManufacturer());
                priceTV.setText(items.getPrice());
                categoryTV.setText(items.getCategory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count <= 0){
                    Toast.makeText(CustomerItemActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Cart").child(customerID);

                    String title = titleTV.getText().toString();
                    String price = priceTV.getText().toString();
                    String manufacturer = manufacturerTV.getText().toString();
                    String amount = Integer.toString(count);

                    HashMap<String, Object> cartHashmap = new HashMap<>();

                    cartHashmap.put("title", title);
                    cartHashmap.put("price", price);
                    cartHashmap.put("manufacturer", manufacturer);
                    cartHashmap.put("itemID", itemID);
                    cartHashmap.put("quantity", amount);

                    ref.child(itemID).setValue(cartHashmap);
                    Toast.makeText(CustomerItemActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
                

            }
        });

        addComment = findViewById(R.id.addComment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(CustomerItemActivity.this);
                mydialog.setTitle("Leave a Comment...");

                final EditText commentET = new EditText(CustomerItemActivity.this);
                commentET.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(commentET);

                mydialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comment = commentET.getText().toString();

                        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
                        Date todayDate = new Date();
                        String date = currentDate.format(todayDate);

                        if(comment.equals("")){
                            Toast.makeText(CustomerItemActivity.this, "Please Enter A Comment", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Comments").child(itemID);

                            HashMap<String, Object> commentHashmap = new HashMap<>();
                            commentHashmap.put("comment", comment);
                            commentHashmap.put("itemID", itemID);
                            commentHashmap.put("email", email);
                            commentHashmap.put("name", firstName);
                            commentHashmap.put("date", date);
                            reference1.push().setValue(commentHashmap);
                        }



                    }
                });

                mydialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

        rateButton = findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateItem();
            }
        });


    }

    private void rateItem() {
        Intent i = getIntent();
        String customerID = i.getStringExtra("customerID");
        String itemID = i.getStringExtra("itemID");

        final AlertDialog.Builder mydialog = new AlertDialog.Builder(CustomerItemActivity.this);
        final RatingBar ratingBar = new RatingBar(this);
        ratingBar.setMax(5);

        mydialog.setTitle("Rate Item");

        ratingBar.setNumStars(5);
        mydialog.setView(ratingBar);

        mydialog.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String val = String.valueOf(ratingBar.getProgress());
                System.out.println(val);

                if(val.equals("")){
                    Toast.makeText(CustomerItemActivity.this, "Please enter a rating", Toast.LENGTH_SHORT).show();
                    ratingBar.requestFocus();
                } else {
                    DatabaseReference reference5 = FirebaseDatabase.getInstance().getReference("Ratings").child(itemID).child(customerID);
                    HashMap<String, Object> ratingHashmap = new HashMap<>();
                    ratingHashmap.put("rating", val);
                    ratingHashmap.put("customerID", customerID);
                    ratingHashmap.put("itemID", itemID);

                    reference5.setValue(ratingHashmap);
                }

            }
        });

        mydialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        mydialog.create();
        mydialog.show();

    }
}