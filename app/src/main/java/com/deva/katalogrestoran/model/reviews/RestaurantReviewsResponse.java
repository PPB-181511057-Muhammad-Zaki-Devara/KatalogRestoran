package com.deva.katalogrestoran.model.reviews;

import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RestaurantReviewsResponse {
    @SerializedName("reviews_count")
    @Expose
    private long reviewsCount;

    @SerializedName("user_reviews")
    @JsonAdapter(ReviewListDeserializer.class)
    @Expose
    private List<Review> reviews;

    public static class ReviewListDeserializer implements JsonDeserializer<List<Review>> {
        @Override
        public List<Review> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonArray reviewJsonArray = json.getAsJsonArray();
            List<Review> reviewList = new ArrayList<>(reviewJsonArray.size());
            for (int i = 0; i < reviewJsonArray.size(); i++){
                JsonObject obj = reviewJsonArray.get(i).getAsJsonObject();
                Gson gson = new Gson();
                reviewList.add(gson.fromJson(obj.get("review").getAsJsonObject(), Review.class));
            }
            return reviewList;
        }
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public long getReviewsCount() {
        return reviewsCount;
    }
}
