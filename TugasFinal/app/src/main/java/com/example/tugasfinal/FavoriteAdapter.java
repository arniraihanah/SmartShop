package com.example.tugasfinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Favorite> favoriteList;
    private Context context;
    private onFavoriteDeleteListener deleteListener;

    public interface onFavoriteDeleteListener {
        void onFavoriteDelete(int position);
    }

    public FavoriteAdapter(List<Favorite> favoriteList, Context context, onFavoriteDeleteListener deleteListener) {
        this.favoriteList = favoriteList;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);
        Picasso.get().load(favorite.getProductImage()).into(holder.ivProduct);
        holder.tvTitle.setText(favorite.getProductTitle());
        holder.tvPrice.setText(String.format("$%.2f", favorite.getProductPrice()));

        holder.itemView.setOnClickListener(v->{
           Intent intent = new Intent(context, DetailActivity.class);
           intent.putExtra("product", favorite.toProduct());
           context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v->{
            FavoriteUtils.removeFavorite(context, favorite);
            favoriteList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, favoriteList.size());
            deleteListener.onFavoriteDelete(position);
        });
    }
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvTitle, tvPrice;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            deleteButton = itemView.findViewById(R.id.btDelete);
        }
    }
}
