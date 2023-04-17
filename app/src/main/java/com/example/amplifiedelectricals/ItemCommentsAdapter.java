package com.example.amplifiedelectricals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemCommentsAdapter extends RecyclerView.Adapter<ItemCommentsAdapter.ItemCommentsViewHolder> {

    private final List<ModelComments> commentList;
    private final Context context;

    public ItemCommentsAdapter(List<ModelComments> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemCommentsAdapter.ItemCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemCommentsAdapter.ItemCommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comments, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCommentsAdapter.ItemCommentsViewHolder holder, int position) {
        ModelComments comments = commentList.get(position);

        holder.firstNameTV.setText(comments.getName() + "  -  ");
        holder.dateTV.setText(comments.getDate());
        holder.commentTV.setText(comments.getComment());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ItemCommentsViewHolder extends RecyclerView.ViewHolder{

        TextView firstNameTV, dateTV, commentTV;

        public ItemCommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            firstNameTV = itemView.findViewById(R.id.firstNameTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            commentTV = itemView.findViewById(R.id.commentTV);

        }
    }

}
