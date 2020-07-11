package com.deva.katalogrestoran.model.restaurants;

import android.util.Log;

import com.deva.katalogrestoran.model.location.Location;
import com.deva.katalogrestoran.model.rating.UserRating;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.osmdroid.util.GeoPoint;

public class Restaurant {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("thumb")
    @Expose
    private String thumbUrl;

    @SerializedName("average_cost_for_two")
    @Expose
    private int averageCostForTwo;

    @SerializedName("price_range")
    @Expose
    private int priceRange;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("user_rating")
    @Expose
    private UserRating userRating;

    @SerializedName("has_online_delivery")
    @Expose
    private int hasOnlineDelivery;

    @SerializedName("is_delivering_now")
    @Expose
    private int isDeliveringNow;

    @SerializedName("location")
    @Expose
    private Location location;

    /* CONSTRUCTOR */
    public Restaurant(
            long id,
            String name,
            String thumbUrl,
            int averageCostForTwo,
            int priceRange,
            String currency,
            UserRating userRating,
            int isDeliveringNow,
            int hasOnlineDelivery,
            Location location) {
        this.id = id;
        this.name = name;
        this.thumbUrl = thumbUrl;
        this.averageCostForTwo = averageCostForTwo;
        this.priceRange = priceRange;
        this.currency = currency;
        this.userRating = userRating;
        this.isDeliveringNow = isDeliveringNow;
        this.hasOnlineDelivery = hasOnlineDelivery;
        this.location = location;
    }

    /* GETTER */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public int getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public int getIsDeliveringNow() {
        return isDeliveringNow;
    }

    public int getHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    public Location getLocation() {
        return location;
    }

//    public static class AggregateRatingDeserializer implements JsonDeserializer<Float> {
//        @Override
//        public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
//            JsonObject obj = json.getAsJsonObject();
//            return obj.get("aggregate_rating").getAsFloat();
//        }
//    }

    public GeoPoint getRestaurantGeoPoint(){
        return new GeoPoint(location.getLatitude(), location.getLongitude());
    }
}
