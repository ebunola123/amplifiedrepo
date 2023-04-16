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

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.CustomerOrderViewHolder> {

    private final List<ModelOrder> orderList;
    private final Context context;
    String customerID;

    public CustomerOrderAdapter(List<ModelOrder> orderList, Context context, String customerID) {
        this.orderList = orderList;
        this.context = context;
        this.customerID = customerID;
    }

    @NonNull
    @Override
    public CustomerOrderAdapter.CustomerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerOrderAdapter.CustomerOrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_date, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderAdapter.CustomerOrderViewHolder holder, int position) {
        ModelOrder order = orderList.get(position);

        /*holder.titleTV.setText(order.getTitle());
        holder.priceTV.setText("  -  €" + order.getPrice());
        holder.manTV.setText(order.getManufacturer());
        holder.quantityTV.setText(order.getQuantity());*/
        holder.dateTV.setText(order.getDate() + "  -  ");
        holder.timeTV.setText(order.getTime());

        //do timestam as well
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerOrderDetailsActicity.class);
                intent.putExtra("orderDate", order.getDate());
                intent.putExtra("customerID", customerID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class CustomerOrderViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV, timeTV;

        public CustomerOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.dateTV);
            timeTV = itemView.findViewById(R.id.timeTV);
        }


    }
}
