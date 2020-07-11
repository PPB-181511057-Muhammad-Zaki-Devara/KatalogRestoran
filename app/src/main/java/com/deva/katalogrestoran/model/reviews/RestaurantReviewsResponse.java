package com.deva.katalogrestoran.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantReviewsResponse {
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }
}
