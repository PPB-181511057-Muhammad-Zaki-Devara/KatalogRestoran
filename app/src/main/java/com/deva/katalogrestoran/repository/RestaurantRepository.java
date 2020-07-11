package com.deva.katalogrestoran.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.deva.katalogrestoran.Config;
import com.deva.katalogrestoran.adapter.RestaurantAdapter;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.reviews.RestaurantReviewsResponse;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.model.search.SearchRestaurantResponse;
import com.deva.katalogrestoran.rest.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RestaurantRepository {
    private static final String TAG = RestaurantRepository.class.getSimpleName();
    private MutableLiveData<List<Restaurant>> mAllRestaurant;


    public RestaurantRepository(Application application) {
        mAllRestaurant = new MutableLiveData<>();
        requestRestaurants();
    }

    void requestRestaurants(){
        API.restaurants().search(Config.API_KEY, "", 11052, "city", "rating").enqueue(new retrofit2.Callback<SearchRestaurantResponse>() {
            @Override
            public void onResponse(Call<SearchRestaurantResponse> call, Response<SearchRestaurantResponse> response) {
                mAllRestaurant.setValue(response.body().getRestaurants());
            }

            @Override
            public void onFailure(Call<SearchRestaurantResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                Log.e(TAG, call.request().toString());
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }

    void requestReviews(long resId, int index){
        API.restaurants().restaurantReviews(Config.API_KEY, resId).enqueue(new retrofit2.Callback<RestaurantReviewsResponse>() {
            @Override
            public void onResponse(Call<RestaurantReviewsResponse> call, Response<RestaurantReviewsResponse> response) {
                mAllRestaurant.getValue().get(index).setReviews(response.body().getReviews());
            }

            @Override
            public void onFailure(Call<RestaurantReviewsResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                Log.e(TAG, call.request().toString());
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }
    public LiveData<List<Restaurant>> getAllRestaurants() {
        return mAllRestaurant;
    }


}
