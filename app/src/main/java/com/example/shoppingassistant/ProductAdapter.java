package com.example.shoppingassistant;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.threeten.bp.Duration;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> products;

    public ProductAdapter(ArrayList<Product> products) {
        this.products = products;

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
        TextView oldPrice;
        TextView quantityLeft;
        TextView timeLeft;
        TextView delete;

        public ProductViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            oldPrice = view.findViewById(R.id.oldPrice);
            quantityLeft = view.findViewById(R.id.quantityLeft);
            timeLeft = view.findViewById(R.id.timeLeft);
            delete = view.findViewById(R.id.delete);

        }

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_v2, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        Picasso.get()
                .load(products.get(position).image)
                .into(holder.image);

        holder.price.setText("Актуальная цена: " + products.get(position).price + " " + products.get(position).priceFormat);
        holder.name.setText(products.get(position).name);
        if (!products.get(position).oldPrice.equals("")) {
            holder.oldPrice.setText("Цена по прейскуранту: " + products.get(position).oldPrice + " " + products.get(position).priceFormat);
        }
        if (products.get(position).chinese) {
            holder.quantityLeft.setText("Ставок: " + products.get(position).bidCount);
        } else {
            holder.quantityLeft.setText("Доступно: " + products.get(position).quantityLeft);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.timeLeft.setText("Осталось времени:" + products.get(position).timeLeft
                .replaceAll("[0-9]+S+", "мин.")
                .replaceAll("M", "")
                .replaceAll("H", "ч. ")
                .replaceAll("D", "дн.")
                .replace('P', ' ')
                .replace('T', ' '));




    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public void reset(int position) {
        MyRequest myRequest = new MyRequest();
        myRequest.execute(products.get(position).url);
            try {
                products.set(position, myRequest.get(15, TimeUnit.SECONDS));
                notifyDataSetChanged();

            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
    }

}