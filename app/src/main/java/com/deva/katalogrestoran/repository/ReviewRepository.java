package com.deva.katalogrestoran.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.deva.katalogrestoran.Config;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.reviews.RestaurantReviewsResponse;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.model.search.SearchRestaurantResponse;
import com.deva.katalogrestoran.rest.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReviewRepository {
        private static final String TAG = ReviewRepository.class.getSimpleName();
        private MutableLiveData<List<Review>> listOfReviews;
        private static ReviewRepository mRepository;

        public static ReviewRepository getInstance(){
            if (mRepository == null){
                mRepository = new ReviewRepository();
            }
            return mRepository;
        }

        public ReviewRepository(){
            listOfReviews = new MutableLiveData<>();
        }

        public MutableLiveData<List<Review>> getListOfReviews(){
            return listOfReviews;
        }


    public MutableLiveData<List<Review>> getReviewsFromAPI(long resId){
        API.restaurants().restaurantReviews(Config.API_KEY, resId).enqueue(new retrofit2.Callback<RestaurantReviewsResponse>() {
            @Override
            public void onResponse(Call<RestaurantReviewsResponse> call, Response<RestaurantReviewsResponse> response) {
                listOfReviews.setValue(response.body().getReviews());
            }

            @Override
            public void onFailure(Call<RestaurantReviewsResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                Log.e(TAG, call.request().toString());
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
                listOfReviews.postValue(null);
            }
        });
        return listOfReviews;
    }

}
