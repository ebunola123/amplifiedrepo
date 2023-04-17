package com.example.amplifiedelectricals.customeritemsearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amplifiedelectricals.R;
import com.example.amplifiedelectricals.models.ModelItems;

import java.util.List;

public class ManufacturerSearchAdapter extends RecyclerView.Adapter<ManufacturerSearchAdapter.ManufacturerSearchViewHolder> {

    private final List<ModelItems> itemList;
    private final Context context;
    String customerID;

    public ManufacturerSearchAdapter(List<ModelItems> itemList, Context context, String customerID) {
        this.itemList = itemList;
        this.context = context;
        this.customerID = customerID;
    }

    @NonNull
    @Override
    public ManufacturerSearchAdapter.ManufacturerSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ManufacturerSearchAdapter.ManufacturerSearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_manufacturer, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ManufacturerSearchAdapter.ManufacturerSearchViewHolder holder, int position) {
        ModelItems items = itemList.get(position);

        holder.manufacturerTV.setText(items.getManufacturer() + "  -  ");
        holder.titleTV.setText(items.getTitle());
        holder.categoryTV.setText(items.getCategory());
        holder.priceTV.setText("€" + items.getPrice());

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

    public class ManufacturerSearchViewHolder extends RecyclerView.ViewHolder{
        TextView manufacturerTV, categoryTV, titleTV, priceTV;

        public ManufacturerSearchViewHolder(@NonNull View itemView) {
            super(itemView);

            manufacturerTV = itemView.findViewById(R.id.manufacturerTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            priceTV = itemView.findViewById(R.id.priceTV);
        }
    }

}
