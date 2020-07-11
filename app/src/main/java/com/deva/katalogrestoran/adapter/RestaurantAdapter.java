package com.deva.katalogrestoran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.deva.katalogrestoran.R;
import com.deva.katalogrestoran.activity.MainActivity;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.task.LoadImageUrl;
import com.deva.katalogrestoran.viewmodel.RestaurantViewModel;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{
    private List<Restaurant> mDataset;

    private RestaurantViewModel vm;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public RestaurantViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public RestaurantAdapter(Context context){
        vm = new ViewModelProvider((MainActivity) context).get(RestaurantViewModel.class);
    }

    public void setDataset(List<Restaurant> dataset){
        this.mDataset = dataset;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        RestaurantViewHolder vh = new RestaurantViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        TextView restaurantName = (TextView) holder.linearLayout.findViewById(R.id.restaurant_name);
        TextView costForTwo = (TextView) holder.linearLayout.findViewById(R.id.cost_for_two);
        TextView currency = (TextView) holder.linearLayout.findViewById(R.id.currency);
        TextView rating = (TextView) holder.linearLayout.findViewById(R.id.rating);
        RatingBar ratingStars = (RatingBar) holder.linearLayout.findViewById(R.id.rating_stars);
        TextView hasOnlineDelivery = (TextView) holder.linearLayout.findViewById(R.id.has_online_delivery);
        ImageView restaurantPhoto = (ImageView) holder.linearLayout.findViewById(R.id.restaurant_photo);

        restaurantName.setText(mDataset.get(position).getName());
        costForTwo.setText(Integer.toString(mDataset.get(position).getAverageCostForTwo()));
        currency.setText(mDataset.get(position).getCurrency());
        rating.setText(Float.toString(mDataset.get(position).getUserRating().getAggregateRating()));
        ratingStars.setRating(mDataset.get(position).getUserRating().getAggregateRating());
        String isDeliveringStatus = mDataset.get(position).getHasOnlineDelivery() == 0 ? "Online Order Not Available" : "Online Order Available";
        hasOnlineDelivery.setText(isDeliveringStatus);
        if(mDataset.get(position).getThumbUrl() != ""){
            new LoadImageUrl(restaurantPhoto).execute(mDataset.get(position).getThumbUrl());
        }else{

        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}