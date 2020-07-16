package com.deva.katalogrestoran.model.rating;

import com.deva.katalogrestoran.model.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRating {
    @SerializedName("aggregate_rating")
    @Expose
    private float aggregateRating;

    public UserRating(float aggregateRating){
        this.aggregateRating = aggregateRating;
    }

    public float getAggregateRating() {
        return aggregateRating;
    }
}
