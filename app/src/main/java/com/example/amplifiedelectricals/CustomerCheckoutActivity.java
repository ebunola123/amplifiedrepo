package com.example.amplifiedelectricals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CustomerCheckoutActivity extends AppCompatActivity {

    //ArrayList<ModelCart> cartList;
    EditText cardNameET, cardNoET, expiryDateET, cvcET, addressLine1ET, addressLine2ET, countyET, countryET;
    TextView totalPriceTV;
    Button purchase;
    CustomerCartListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ModelCart> cartList;

    ArrayList<String> idList;
    ArrayList<String> quantityList;
    ArrayList<String> priceList;
    ArrayList<String> titleList;
    ArrayList<String> manufacturerList;
    ArrayList<String> updateList = new ArrayList<String>();;

    String orderItemID, orderQuantity;
    String currentStock;
    String id;
    String newStock;

    int a;
    int b;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);
        
        //when checkout button is clicked, get the quantity and subtract it from the item ids in stock db


        //open stock db - get the item ID,
        //define orderITemID outside the scope
        //if orderItemID = stockItemID:
        //stockItemQuantity - orderQuantity
        //set stockItemQuanity to new value

        //put in a for loop depending on the list of item ids in cart



        //do a db quaery where itemID == orderItemID
        //if exists, do stockItemQuantity - orderQuantity




       // DatabaseReference reference =

        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();

        if(currentUser==null){
            Intent intent=new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
        }

        String customerID = auth.getUid();


        Intent i = getIntent();
        String total = i.getStringExtra("totalPrice");
        System.out.println("MY TOTAL - " + total);

        double totalPrice = Double.parseDouble(total);

        totalPriceTV = findViewById(R.id.totalPriceTV);
        totalPriceTV.setText("Total Price: €" + totalPrice);

        //payment details
        cardNameET = findViewById(R.id.cardNameET);
        cardNoET = findViewById(R.id.cardNoET);
        expiryDateET = findViewById(R.id.expiryDateET);
        cvcET = findViewById(R.id.cvcET);

        //deleivery address details
        addressLine1ET = findViewById(R.id.addressLine1ET);
        addressLine2ET = findViewById(R.id.addressLine2ET);
        countyET = findViewById(R.id.countyET);
        countryET = findViewById(R.id.countryET);



        //recycler view details
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        adapter = new CustomerCartListAdapter(cartList, this);
        recyclerView.setAdapter(adapter);

        idList = new ArrayList<String>();
        quantityList = new ArrayList<String>();
        titleList = new ArrayList<String>();
        priceList = new ArrayList<String>();
        manufacturerList = new ArrayList<String>();
        //updateList = new ArrayList<String>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart").child(customerID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelCart cart = dataSnapshot.getValue(ModelCart.class);
                    cartList.add(cart);

                    orderItemID = cart.getItemID();
                    orderQuantity = cart.getQuantity();

                    quantityList.add(cart.getQuantity());
                    idList.add(cart.getItemID());
                    priceList.add(cart.getPrice());
                    titleList.add(cart.getTitle());
                    manufacturerList.add(cart.getManufacturer());
                    //totalPriceList.add(cart.get);
                    System.out.println("idlist" + idList);
                    System.out.println("quantitylist" + quantityList);


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Customers").child(customerID);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCustomers customer = snapshot.getValue(ModelCustomers.class);

                addressLine1ET.setText(customer.getAddressLine1());
                addressLine2ET.setText(customer.getAddressLine2());
                countyET.setText(customer.getCounty());
                countryET.setText(customer.getCountry());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //open stock db
        //if cartlist.contains any of the values

        purchase = findViewById(R.id.purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> newList = new ArrayList<String>();;

                HashMap<String, Object> stockHashmap = new HashMap<>();

                int i;
                for(i=0; i<idList.size(); i++){

                    /*if(i>1){
                        idList.clear();
                        idList.remove()
                      //  idList.remove(i-1); //to get rid of the last index value
                    }*/

                    id = idList.get(i);

                    String myID= idList.get(i);
                    String qList = quantityList.get(i);
                    String price = priceList.get(i);
                    String title = titleList.get(i);
                    String manufacturer = manufacturerList.get(i);
                    //String totalPrice;

                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
                    Date todayDate = new Date();
                    String date = currentDate.format(todayDate);


                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    System.out.println("currentTime: " + currentTime);

                    String date_time = date + "-" + currentTime;

                    //create order db
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Orders").child(customerID);
                    HashMap<String, Object> orderHashmap = new HashMap<>();
                    orderHashmap.put("itemID", myID);
                    orderHashmap.put("title", title);
                    orderHashmap.put("manufacturer", manufacturer);
                    orderHashmap.put("quantity",qList);
                    orderHashmap.put("date",date);
                    orderHashmap.put("time", currentTime);
                    orderHashmap.put("price", price);
                    orderHashmap.put("totalPrice", total);

                    String orderID = reference2.push().getKey();
                    orderHashmap.put("orderID",orderID);
                    reference2.child(myID).setValue(orderHashmap);



                    DatabaseReference reference5 = FirebaseDatabase.getInstance().getReference("Cart").child(customerID);
                    reference5.removeValue();




                    System.out.println("orderid " + myID + "quantity: " + qList);

                   DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("StockLevel").child(id);
                   reference3.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           ModelStock stock = snapshot.getValue(ModelStock.class);

                           System.out.println("ItemID - " + myID);

                           String currentStock = stock.getStock();
                            a = Integer.parseInt(currentStock);
                            System.out.println("a: " +a);
                            b = Integer.parseInt(qList);
                           System.out.println("b: " +b);

                           int c = a - b;
                           System.out.println("c: " +c);
                           newStock = "0";
                           newStock = Integer.toString(c);
                           updateList.add(newStock);
                           newList.add(newStock);
                           System.out.println("NEW LIST IN DB REF LOOP: " + newList);


                           //PUT AS PARAMS AND SEND TO NEW METHOD

                           //System.out.println("newstock " + newStock);
                          // System.out.println("id " + myID);

                          // updateStock(id, newStock);
                           /*HashMap<String, Object> stockHashmap = new HashMap<>();
                            */
                           /*stockHashmap.put("itemID", id);
                           stockHashmap.put("stock", newStock);*/

                           /*reference3.setValue(stockHashmap);*/

                           stockHashmap.clear();
                           stockHashmap.put("itemID", myID);
                           stockHashmap.put("stock", newStock);


                           /*ModelStock ms = new ModelStock("myID", "newStock");
                           ms.setItemID(myID);
                           ms.setStock(newStock);*/
                          /* stockHashmap.clear();
                           stockHashmap.put("itemID", myID);
                           stockHashmap.put("stock", newStock);*/
                        //   System.out.println("MS " + ms.getItemID() + "Stock: " + ms.getStock());
                         /*  if(snapshot.exists()){
                              // reference3.removeValue();
                               // reference3.updateChildren(stockHashmap);
                               reference3.setValue(ms);
                               reference3.setValue(stockHashmap);
                               finish();

                           }*/



                         //  reference3.setValue(stockHashmap);
                           //THIS WORKED - WRONG ID FOR FIRST, AND APP SHUT DOWN
                           reference3.removeValue();
                          // reference3.updateChildren(stockHashmap);
                           reference3.setValue(stockHashmap);

                         /// reference3.push().setValue(stockHashmap);
                         // finish();
                         // reference3.updateChildren(stockHashmap);
                         // finish();
                           //trypush



                           Toast.makeText(CustomerCheckoutActivity.this, "Order complete", Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

                   System.out.println("NEW LIST IN FOR LOOP: " + newList);


//DONNTTTTTTTT DOOO IT HEREEEEEEEEEE
                  //  reference3.removeValue();
                    // reference3.updateChildren(stockHashmap);
                   // reference3.setValue(stockHashmap);
                    //reference3.setValue(stockHashmap);

                    /*stockHashmap.put("itemID", id);
                    stockHashmap.put("stock", newStock);
                    reference3.setValue(stockHashmap);*/


                   // updateList.add(newStock);
                    //System.out.println("REAL UPDATELIST " + updateList);




                    /*HashMap<String, Object> stockHashmap = new HashMap<>();

                    stockHashmap.put("itemID", id);
                    stockHashmap.put("stock", newStock);
                    reference3.setValue(stockHashmap);*/

                   // System.out.println("newstock: " + newStock);


                   // reference3.setValue(stockHashmap);

                    //updateStock(myid, newStock);

                  //  DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("StockLevel").child(myID);
                  //  reference4.setValue(stockHashmap);

                    //create new db for stock?

                } //end for loop
                System.out.println("NEW LIST OUTSIDE FOR LOOP: " + newList);

              //  Intent intent = new Intent(CustomerCheckoutActivity.this, AfterOrderActivity.class);
              //  startActivity(intent);

                //create new for loop
                //create new list storing new stock in it!
               // System.out.println("UPDATELIST" + updateList);



              /* for(int j=0; j<idList.size(); j++){
                    String itemID = idList.get(j);
                    String updatedQuantity = updateList.get(j);

                    HashMap<String, Object> updateHashmap = new HashMap<>();

                    updateHashmap.put("itemID", itemID);
                    updateHashmap.put("stock", updatedQuantity);
                   // reference3.setValue(stockHashmap);

                    DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("StockLevel").child(itemID);
                    reference4.setValue(updateHashmap);



                }*/











            }
        });
        
        
    }


    public void updateStock(String id, String newStock){
        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("StockLevel").child(id);
        HashMap<String, Object> stockHashmap = new HashMap<>();

        stockHashmap.put("itemID", id);
        stockHashmap.put("stock", newStock);
        reference3.setValue(stockHashmap);

    }
}