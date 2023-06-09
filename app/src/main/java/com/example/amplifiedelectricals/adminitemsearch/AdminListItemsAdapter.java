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

public class AdminListItemsAdapter extends RecyclerView.Adapter<AdminListItemsAdapter.AdminListItemsViewHolder> {

    private final List<ModelItems> itemList;
    private final Context context;

    public AdminListItemsAdapter(List<ModelItems> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public AdminListItemsAdapter.AdminListItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminListItemsAdapter.AdminListItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_admin_items, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminListItemsAdapter.AdminListItemsViewHolder holder, int position) {
        ModelItems items = itemList.get(position);

        holder.titleTV.setText(items.getTitle());
        holder.priceTV.setText(items.getPrice());
        holder.categoryTV.setText(items.getCategory());
        holder.manTV.setText(items.getManufacturer());

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

    public class AdminListItemsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV, priceTV, categoryTV, manTV;

        public AdminListItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            manTV = itemView.findViewById(R.id.manTV);
        }
    }
}
