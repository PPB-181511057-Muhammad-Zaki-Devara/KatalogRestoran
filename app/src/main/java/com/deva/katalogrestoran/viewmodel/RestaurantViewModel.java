package com.deva.katalogrestoran.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.repository.RestaurantRepository;

import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {
    private RestaurantRepository mRepository;

    private LiveData<List<Restaurant>> mAllRestaurants;

    public RestaurantViewModel (Application application) {
        super(application);
        mRepository = new RestaurantRepository(application);
        mAllRestaurants = mRepository.getAllRestaurants();
    }

    public LiveData<List<Restaurant>> getAllRestaurants() { return mAllRestaurants; }

}
