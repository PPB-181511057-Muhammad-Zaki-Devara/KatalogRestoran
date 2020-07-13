package com.deva.katalogrestoran.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.repository.RestaurantRepository;

import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {
    private RestaurantRepository mRepository;

    private MutableLiveData<List<Restaurant>> listOfRestaurants;

    public RestaurantViewModel (Application application) {
        super(application);
        mRepository = RestaurantRepository.getInstance();
    }

    public MutableLiveData<List<Restaurant>> getListOfRestaurants(){
        return listOfRestaurants;
    }

    public MutableLiveData<List<Restaurant>> searchRestaurants(String query, long entityId, String entityType, String sort) {
        listOfRestaurants = mRepository.searchRestaurants(query, entityId, entityType, sort);
        return listOfRestaurants;
    }

}
