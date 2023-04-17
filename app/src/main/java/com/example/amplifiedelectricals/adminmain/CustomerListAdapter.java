package com.example.amplifiedelectricals.adminmain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amplifiedelectricals.R;
import com.example.amplifiedelectricals.models.ModelCustomers;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerListViewHolder> {

    private final List<ModelCustomers> customerList;
    private final Context context;

    public CustomerListAdapter(List<ModelCustomers> customerList, Context context) {
        this.customerList = customerList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerListAdapter.CustomerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerListAdapter.CustomerListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_customer_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.CustomerListViewHolder holder, int position) {
        ModelCustomers customers = customerList.get(position);

        holder.firstNameTV.setText(customers.getFirstName());
        holder.lastNameTV.setText(customers.getLastName());
        holder.emailTV.setText(customers.getEmail());
        holder.phoneNumberTV.setText(customers.getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminCustomerProfileActivity.class);
                intent.putExtra("email", customers.getEmail());
                intent.putExtra("customerID", customers.getCustomerID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class CustomerListViewHolder extends RecyclerView.ViewHolder{

        TextView firstNameTV, lastNameTV, emailTV, phoneNumberTV;

        public CustomerListViewHolder(@NonNull View itemView) {
            super(itemView);

            firstNameTV = itemView.findViewById(R.id.firstNameTV);
            lastNameTV = itemView.findViewById(R.id.lastNameTV);
            emailTV = itemView.findViewById(R.id.emailTV);
            phoneNumberTV = itemView.findViewById(R.id.phoneNumberTV);

        }
    }
}
