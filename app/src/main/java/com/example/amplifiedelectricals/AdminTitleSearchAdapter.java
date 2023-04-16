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

public class AdminTitleSearchAdapter extends RecyclerView.Adapter<AdminTitleSearchAdapter.AdminTitleSearchViewHolder> {

    private final List<ModelItems> itemList;
    private final Context context;

    public AdminTitleSearchAdapter(List<ModelItems> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminTitleSearchAdapter.AdminTitleSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminTitleSearchAdapter.AdminTitleSearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_title, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTitleSearchAdapter.AdminTitleSearchViewHolder holder, int position) {
        ModelItems items = itemList.get(position);
        holder.titleTV.setText(items.getTitle() + "  -  ");
        holder.categoryTV.setText(items.getCategory());
        holder.manufacturerTV.setText(items.getManufacturer());
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
    public class AdminTitleSearchViewHolder extends RecyclerView.ViewHolder{
        TextView titleTV, categoryTV, manufacturerTV, priceTV;

        public AdminTitleSearchViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            manufacturerTV = itemView.findViewById(R.id.manufacturerTV);
            priceTV = itemView.findViewById(R.id.priceTV);

        }
    }
}
