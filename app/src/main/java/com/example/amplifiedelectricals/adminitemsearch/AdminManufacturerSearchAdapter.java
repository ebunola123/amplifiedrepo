package com.example.amplifiedelectricals.adminitemsearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amplifiedelectricals.adminitems.ItemProfileActivity;
import com.example.amplifiedelectricals.models.ModelItems;
import com.example.amplifiedelectricals.R;

import java.util.List;

public class AdminManufacturerSearchAdapter extends RecyclerView.Adapter<AdminManufacturerSearchAdapter.AdminManufacturerSearchViewHolder> {

    private final List<ModelItems> itemList;
    private final Context context;

    public AdminManufacturerSearchAdapter(List<ModelItems> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminManufacturerSearchAdapter.AdminManufacturerSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminManufacturerSearchAdapter.AdminManufacturerSearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_manufacturer, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminManufacturerSearchAdapter.AdminManufacturerSearchViewHolder holder, int position) {
        ModelItems items = itemList.get(position);

        holder.manufacturerTV.setText(items.getManufacturer());
        holder.categoryTV.setText("  -  " + items.getCategory());
        holder.titleTV.setText(items.getTitle());
        holder.priceTV.setText("€" + items.getPrice());

        String itemID = items.getItemID();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemProfileActivity.class);
                intent.putExtra("itemID", itemID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class AdminManufacturerSearchViewHolder extends RecyclerView.ViewHolder{
        TextView manufacturerTV, categoryTV, titleTV, priceTV;

        public AdminManufacturerSearchViewHolder(@NonNull View itemView) {
            super(itemView);

            manufacturerTV = itemView.findViewById(R.id.manufacturerTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            priceTV = itemView.findViewById(R.id.priceTV);
        }
    }

}
