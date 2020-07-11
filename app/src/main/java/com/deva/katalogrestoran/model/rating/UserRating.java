package com.deva.katalogrestoran.model.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRating {
    @SerializedName("aggregate_rating")
    @Expose
    private float aggregateRating;

    public float getAggregateRating() {
        return aggregateRating;
    }
}
