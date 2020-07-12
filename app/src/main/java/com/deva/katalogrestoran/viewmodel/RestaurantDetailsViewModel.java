package com.deva.katalogrestoran.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.repository.RestaurantRepository;
import com.deva.katalogrestoran.repository.ReviewRepository;

import java.util.List;

public class RestaurantDetailsViewModel extends AndroidViewModel {
    private RestaurantRepository restaurantRepository;
    private ReviewRepository reviewRepository;

    private MutableLiveData<Restaurant> mRestaurant;
    private MutableLiveData<List<Review>> listOfReviews;

    public RestaurantDetailsViewModel (Application application) {
        super(application);
        restaurantRepository = RestaurantRepository.getInstance();
        reviewRepository = ReviewRepository.getInstance();
    }

    public MutableLiveData<Restaurant> getRestaurant(long resId){
        mRestaurant = restaurantRepository.find(resId);
        if(mRestaurant.getValue() == null){
            mRestaurant = restaurantRepository.getRestaurantFromAPI(resId);
        }
        return mRestaurant;
    }

    public MutableLiveData<List<Review>> getListOfReviews(long resId) {
        listOfReviews = reviewRepository.getReviewsFromAPI(resId);
        return listOfReviews;
    }
}
