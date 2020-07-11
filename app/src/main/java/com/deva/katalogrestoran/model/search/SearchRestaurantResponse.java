package com.deva.katalogrestoran.model.search;

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

public class SearchRestaurantResponse {
    @SerializedName("results_found")
    @Expose
    private long resultsFound;

    @SerializedName("results_start")
    @Expose
    private int resultsStart;

    @SerializedName("results_shown")
    @Expose
    private int resultsShown;

    @SerializedName("restaurants")
    @JsonAdapter(RestaurantListDeserializer.class)
    @Expose
    private List<Restaurant> restaurants = null;

    public static class RestaurantListDeserializer implements JsonDeserializer<List<Restaurant>> {
        @Override
        public List<Restaurant> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonArray restaurantJsonArray = json.getAsJsonArray();
            List<Restaurant> restaurantList = new ArrayList<>(restaurantJsonArray.size());
            for (int i = 0; i < restaurantJsonArray.size(); i++){
                JsonObject obj = restaurantJsonArray.get(i).getAsJsonObject();
                Gson gson = new Gson();
                restaurantList.add(gson.fromJson(obj.get("restaurant").getAsJsonObject(), Restaurant.class));
            }
            return restaurantList;
        }
    }

    public long getResultsFound() {
        return resultsFound;
    }

    public int getResultsStart() {
        return resultsStart;
    }

    public int getResultsShown() {
        return resultsShown;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
