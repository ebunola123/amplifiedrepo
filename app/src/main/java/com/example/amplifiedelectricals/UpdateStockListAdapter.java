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

public class UpdateStockListAdapter extends RecyclerView.Adapter<UpdateStockListAdapter.UpdateStockListViewHolder>{

    private final List<ModelItems> itemList;
    private final Context context;

    public UpdateStockListAdapter(List<ModelItems> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public UpdateStockListAdapter.UpdateStockListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpdateStockListAdapter.UpdateStockListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_admin_items, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateStockListAdapter.UpdateStockListViewHolder holder, int position) {
        ModelItems items = itemList.get(position);

        holder.titleTV.setText(items.getTitle());
        holder.priceTV.setText("€" + items.getPrice());
        holder.categoryTV.setText(items.getCategory());
        holder.manTV.setText(" - " + items.getManufacturer());

        String itemID = items.getItemID();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateStockActivity.class);
                intent.putExtra("itemID", itemID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class UpdateStockListViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV, priceTV, categoryTV, manTV;

        public UpdateStockListViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            manTV = itemView.findViewById(R.id.manTV);
        }
    }

}
