package com.example.amplifiedelectricals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.CategorySearchViewHolder> {

    private final List<ModelItems> itemList;
    private final Context context;
    String customerID;

    public CategorySearchAdapter(List<ModelItems> itemList, Context context, String customerID) {
        this.itemList = itemList;
        this.context = context;
        this.customerID = customerID;
    }

    @NonNull
    @Override
    public CategorySearchAdapter.CategorySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategorySearchAdapter.CategorySearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_category, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySearchAdapter.CategorySearchViewHolder holder, int position) {
        ModelItems items = itemList.get(position);

        holder.categoryTV.setText(items.getCategory() + "  -  ");
        holder.titleTV.setText(items.getTitle());
        holder.priceTV.setText("€" + items.getPrice());
        holder.manufacturerTV.setText(items.getManufacturer());

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

    public class CategorySearchViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTV, titleTV, manufacturerTV, priceTV;

        public CategorySearchViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTV = itemView.findViewById(R.id.categoryTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            manufacturerTV = itemView.findViewById(R.id.manufacturerTV);
            priceTV = itemView.findViewById(R.id.priceTV);
        }
    }
}
