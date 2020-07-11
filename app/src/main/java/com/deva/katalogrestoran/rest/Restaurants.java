package com.deva.katalogrestoran.rest;

import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.reviews.RestaurantReviewsResponse;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.model.search.SearchRestaurantResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Restaurants {
    //RESTAURANT SEARCH
    @GET("search")
    Call<SearchRestaurantResponse> search(
            @Header("user-key") String userKey,
            @Query("q") String query,
            @Query("entity_id") int entityId,
            @Query("entity_type") String entityType,
            @Query("sort") String sort);

    //RESTAURANT DETAIL
    @GET("restaurant")
    Call<Restaurant> restaurantDetails(@Header("user-key") String userKey, @Query("res_id") long resId);

    //RESTAURANT REVIEWS
    @GET("reviews")
    Call<RestaurantReviewsResponse> restaurantReviews(@Header("user-key") String userKey, @Query("res_id") long resId);

}
