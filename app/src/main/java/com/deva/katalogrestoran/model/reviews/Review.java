package com.deva.katalogrestoran.model.reviews;

import com.deva.katalogrestoran.model.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("rating")
    @Expose
    private float rating;

    @SerializedName("review_text")
    @Expose
    private String reviewText;

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("timestamp")
    @Expose
    private long timestamp;

    @SerializedName("user")
    @Expose
    private User user =  null;

    public float getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }
}
