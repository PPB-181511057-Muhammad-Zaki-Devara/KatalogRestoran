package com.deva.katalogrestoran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deva.katalogrestoran.R;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.task.LoadImageUrl;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> mDataset;
    private Context context;

    public ReviewAdapter(Context context){
        this.context = context;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public ReviewViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        ReviewAdapter.ReviewViewHolder vh = new ReviewAdapter.ReviewViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        TextView userName = (TextView) holder.linearLayout.findViewById(R.id.user_name);
        TextView rating = (TextView) holder.linearLayout.findViewById(R.id.rating_user);
        RatingBar ratingStars = (RatingBar) holder.linearLayout.findViewById(R.id.rating_stars_user);
        TextView review = (TextView) holder.linearLayout.findViewById(R.id.review);
        ImageView profileImage = (ImageView) holder.linearLayout.findViewById(R.id.profile_image);

        if(mDataset.get(position).getUser() != null){
            userName.setText(mDataset.get(position).getUser().getName());
            new LoadImageUrl(profileImage).execute(mDataset.get(position).getUser().getProfileImageUrl());
        }


        rating.setText(Float.toString(mDataset.get(position).getRating()));
        ratingStars.setRating(mDataset.get(position).getRating());
        review.setText(mDataset.get(position).getReviewText());
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setDataset(List<Review> reviews){
        mDataset = reviews;
        notifyDataSetChanged();
    }
}
