package com.thesis.famlink2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.thesis.famlink2.data.Family;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {

    private Context context;
    private List<Family> list;

    public FamilyAdapter(Context context, List<Family> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public FamilyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fam_list, parent, false);
        return new FamilyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FamilyAdapter.ViewHolder holder, int position) {
        Family family = list.get(position);
        holder.textViewFamilyName.setText(family.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        public TextView textViewFamilyName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewFamilyName = itemView.findViewById(R.id.textViewFamilyName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int adapterPosition = getAdapterPosition();
        }

        @Override
        public boolean onLongClick(View v) {
            Context context = v.getContext();
            int adapterPosition = getAdapterPosition();
            return true;
        }
    }
}