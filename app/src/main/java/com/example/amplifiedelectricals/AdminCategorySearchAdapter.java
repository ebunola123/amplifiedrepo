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

public class AdminCategorySearchAdapter extends RecyclerView.Adapter<AdminCategorySearchAdapter.AdminCategorySearchViewHolder> {

    private final List<ModelItems> itemList;
    private final Context context;

    public AdminCategorySearchAdapter(List<ModelItems> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminCategorySearchAdapter.AdminCategorySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminCategorySearchAdapter.AdminCategorySearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_category, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategorySearchAdapter.AdminCategorySearchViewHolder holder, int position) {
        ModelItems items = itemList.get(position);

        holder.categoryTV.setText(items.getCategory() + "  -  ");
        holder.titleTV.setText(items.getTitle());
        holder.priceTV.setText("€" + items.getPrice());
        holder.manufacturerTV.setText(items.getManufacturer());

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

    public class AdminCategorySearchViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTV, titleTV, manufacturerTV, priceTV;

        public AdminCategorySearchViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTV = itemView.findViewById(R.id.categoryTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            manufacturerTV = itemView.findViewById(R.id.manufacturerTV);
            priceTV = itemView.findViewById(R.id.priceTV);
        }
    }
}
