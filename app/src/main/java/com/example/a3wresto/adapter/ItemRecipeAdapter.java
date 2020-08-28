package com.example.a3wresto.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3wresto.R;
import com.example.a3wresto.activity.MainActivity;
import com.example.a3wresto.adapter.ItemRecipe;
import com.example.a3wresto.manager.WsManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemRecipeAdapter extends RecyclerView.Adapter<ItemRecipeAdapter.MyViewHolder> {

    private List<ItemRecipe> itemRecipes;
    private ItemRecipeAdapter.ItemClickListener itemClickListener;

    public ItemRecipeAdapter(List<ItemRecipe> itemRecipes, ItemRecipeAdapter.ItemClickListener listener) {
        this.itemRecipes = itemRecipes;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ItemRecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecipeAdapter.MyViewHolder holder, int position) {
        // 3) AOUJTER CONTENU
        ItemRecipe itemRecipe = itemRecipes.get(position);

        int randomLike = 0 + (int)(Math.random() * ((60 - 0) + 1));
        String randomLikeString = String.valueOf(randomLike);

        holder.titleTextView.setText(itemRecipe.getTitle());
        Picasso.get().load(itemRecipe.getPhoto()).into(holder.photoImageView);
        holder.starsRatingBar.setRating((float) itemRecipe.getNote());
        holder.likesTextView.setText(randomLikeString);

    }



    @Override
    public int getItemCount() {
        return itemRecipes.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // 1) DECLARATION
        TextView titleTextView;
        ImageView photoImageView;
        RatingBar starsRatingBar;
        TextView likesTextView;

        private ItemRecipeAdapter.ItemClickListener itemClickListener;


        MyViewHolder(View v, ItemClickListener itemClickListener) {
            super(v);
            this.itemClickListener = itemClickListener;
            // 2) INITIALISATION
            titleTextView = v.findViewById(R.id.title_text_view);
            photoImageView = v.findViewById(R.id.photo_image_view);
            starsRatingBar = v.findViewById(R.id.rating_bar);
            likesTextView = v.findViewById(R.id.numbLike);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("test", "onClick " + position);
            if(itemClickListener != null) {
                itemClickListener.onClickListener(position);
            }
        }
    }

    public interface ItemClickListener {
        void onClickListener(int position);
    }

}
