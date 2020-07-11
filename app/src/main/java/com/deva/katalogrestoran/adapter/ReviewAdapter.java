package com.deva.katalogrestoran.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deva.katalogrestoran.R;
import com.deva.katalogrestoran.model.restaurants.Restaurant;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<Restaurant> mDataset;

    public ReviewAdapter(ArrayList<Restaurant> dataset){
        this.mDataset = dataset;
    }



    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        //            public TextView restaurantName;
//            public TextView costForTwo;
//            public TextView rating;
//            public ImageView restaurantPhoto;
        public LinearLayout linearLayout;

        public RestaurantViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        RestaurantAdapter.RestaurantViewHolder vh = new RestaurantAdapter.RestaurantViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
