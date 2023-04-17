package com.example.amplifiedelectricals.customercheckout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amplifiedelectricals.models.ModelCart;
import com.example.amplifiedelectricals.R;

import java.util.List;

public class CustomerCartListAdapter extends RecyclerView.Adapter<CustomerCartListAdapter.CustomerCartListViewHolder>{

    private final List<ModelCart> cartList;
    private final Context context;

    public CustomerCartListAdapter(List<ModelCart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerCartListAdapter.CustomerCartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerCartListAdapter.CustomerCartListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerCartListAdapter.CustomerCartListViewHolder holder, int position) {
        ModelCart cart = cartList.get(position);

        holder.titleTV.setText(cart.getTitle());
        holder.priceTV.setText("€" + cart.getPrice());
        holder.quantityTV.setText(" - Quantity: " + cart.getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CustomerCartListViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV, priceTV, quantityTV;

        public CustomerCartListViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            quantityTV = itemView.findViewById(R.id.quantityTV);

        }
    }
}
