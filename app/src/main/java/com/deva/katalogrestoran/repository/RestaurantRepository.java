package com.deva.katalogrestoran.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.rest.API;

import java.util.List;

public class RestaurantRepository {
    private LiveData<List<Restaurant>> mAllRestaurant;


    RestaurantRepository(Application application) {
    }

    LiveData<List<Restaurant>> getAllTasks() {
        return mAllRestaurant;
    }
}
