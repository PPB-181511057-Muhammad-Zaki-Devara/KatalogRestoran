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
import com.deva.katalogrestoran.rest.Restaurants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RestaurantRepository {
    private static final String TAG = RestaurantRepository.class.getSimpleName();
    private MutableLiveData<List<Restaurant>> listOfRestaurants;
    private  MutableLiveData<Restaurant> mRestaurant;
    private static RestaurantRepository mRepository;


    public static RestaurantRepository getInstance(){
        if (mRepository == null){
            mRepository = new RestaurantRepository();
        }
        return mRepository;
    }

    public RestaurantRepository(){
        listOfRestaurants = new MutableLiveData<>();
    }

    public MutableLiveData<List<Restaurant>> getListOfRestaurants(){
        return listOfRestaurants;
    }

    public MutableLiveData<List<Restaurant>> searchRestaurants(String query, long entityId, String entityType, String sort) {
        API.restaurants().search(Config.API_KEY, query, entityId, entityType, sort).enqueue(new retrofit2.Callback<SearchRestaurantResponse>() {
            @Override
            public void onResponse(Call<SearchRestaurantResponse> call, Response<SearchRestaurantResponse> response) {
                listOfRestaurants.setValue(response.body().getRestaurants());
            }

            @Override
            public void onFailure(Call<SearchRestaurantResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                Log.e(TAG, call.request().toString());
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
                listOfRestaurants.postValue(null);
            }
        });
        return listOfRestaurants;
    }

    public MutableLiveData<Restaurant> find(long resId){
        MutableLiveData<Restaurant> result = new MutableLiveData<>();
        result.postValue(null);
        for (Restaurant r: listOfRestaurants.getValue()) {
            if(r.getId() == resId){
                result.setValue(r);
                break;
            }
        }
        return result;
    }

    public MutableLiveData<Restaurant> getRestaurantFromAPI(long resId){
        API.restaurants().restaurantDetails(Config.API_KEY, resId).enqueue(new retrofit2.Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                mRestaurant.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e(TAG, t.toString());
                Log.e(TAG, call.request().toString());
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
                mRestaurant.postValue(null);
            }
        });
        return mRestaurant;
    }
}
