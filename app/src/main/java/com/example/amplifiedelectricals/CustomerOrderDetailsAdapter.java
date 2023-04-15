package com.example.amplifiedelectricals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerOrderDetailsAdapter extends RecyclerView.Adapter<CustomerOrderDetailsAdapter.CustomerOrderDetailsViewHolder> {

    private final List<ModelOrder> orderList;
    private final Context context;

    public CustomerOrderDetailsAdapter(List<ModelOrder> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerOrderDetailsAdapter.CustomerOrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerOrderDetailsAdapter.CustomerOrderDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_orders_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderDetailsAdapter.CustomerOrderDetailsViewHolder holder, int position) {
        ModelOrder order = orderList.get(position);

        holder.titleTV.setText(order.getTitle());
        holder.priceTV.setText("  -  €" + order.getPrice());
        holder.manTV.setText(order.getManufacturer());
        holder.quantityTV.setText(order.getQuantity());
        holder.dateTV.setText(order.getDate());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class CustomerOrderDetailsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV, priceTV, manTV, quantityTV, dateTV;

        public CustomerOrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            manTV = itemView.findViewById(R.id.manTV);
            quantityTV = itemView.findViewById(R.id.quantityTV);
            dateTV = itemView.findViewById(R.id.dateTV);

        }
    }


}
