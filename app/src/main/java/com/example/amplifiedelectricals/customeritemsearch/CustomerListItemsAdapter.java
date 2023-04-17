package com.example.amplifiedelectricals.customeritemsearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amplifiedelectricals.models.ModelItems;
import com.example.amplifiedelectricals.R;

import java.util.List;

public class CustomerListItemsAdapter extends RecyclerView.Adapter<CustomerListItemsAdapter.CustomerListItemsViewHolder> {

    private final List<ModelItems> itemList;
    private final Context context;
    String customerID;

    public CustomerListItemsAdapter(List<ModelItems> itemList, Context context, String customerID) {
        this.itemList = itemList;
        this.context = context;
        this.customerID = customerID;
    }

    @NonNull
    @Override
    public CustomerListItemsAdapter.CustomerListItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerListItemsAdapter.CustomerListItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_admin_items, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListItemsAdapter.CustomerListItemsViewHolder holder, int position) {
        ModelItems items = itemList.get(position);

        holder.titleTV.setText(items.getTitle());
        holder.priceTV.setText(items.getPrice());
        holder.categoryTV.setText(items.getCategory());
        holder.manTV.setText(items.getManufacturer());

        String itemID = items.getItemID();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerItemActivity.class);
                intent.putExtra("itemID", itemID);
                intent.putExtra("customerID", customerID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CustomerListItemsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV, priceTV, categoryTV, manTV;

        public CustomerListItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            manTV = itemView.findViewById(R.id.manTV);

        }
    }

}
