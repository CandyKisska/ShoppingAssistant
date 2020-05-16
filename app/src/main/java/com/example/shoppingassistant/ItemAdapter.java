package com.example.shoppingassistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        public ItemViewHolder(View view) {
            super(view);

        }

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {


    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}


