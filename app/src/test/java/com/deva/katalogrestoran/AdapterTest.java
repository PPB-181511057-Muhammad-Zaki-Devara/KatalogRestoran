package com.deva.katalogrestoran;


import com.deva.katalogrestoran.adapter.RestaurantAdapter;
import com.deva.katalogrestoran.adapter.ReviewAdapter;
import com.deva.katalogrestoran.model.location.Location;
import com.deva.katalogrestoran.model.rating.UserRating;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.model.user.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
//Mengetes Adapter
public class AdapterTest {

    //RestaurantAdapter
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> restaurantList = new ArrayList<>();

    @Before
    public void setup(){
        restaurantAdapter = new RestaurantAdapter(null);
        reviewAdapter = new ReviewAdapter(null);
    }

    @Test
    public void getCountRestaurants(){
        int resultRestaurants = restaurantAdapter.getItemCount();
        assertNotNull(resultRestaurants);
    }

    @Test
    public void setDataRestaurant(){
        restaurantList.add(
                new Restaurant(
                        16774318,
                        "Martabak San Fransisco",
                        "https://b.zmtcdn.com/data/pictures/chains/8/16774318/a54deb9e4dbb79dd7c8091b30c642077_featured_thumb.png",
                        60,
                        2,
                        "$",
                        new UserRating(3.7f),
                        0,
                        0,
                        new Location(40.732013, -73.996155)

                )
        );


        assertNotNull(restaurantList);
    }

    //ReviewAdapter
    ReviewAdapter reviewAdapter;
    List<Review> reviewList = new ArrayList<>();
    @Test
    public void getCountReviews(){
        int resultReviews = reviewAdapter.getItemCount();
        assertNotNull(resultReviews);
    }

    @Test
    public void setDataReview(){
        reviewList.add(
                new Review(
                        3.0f,
                        "Enak Bergizi",
                        1,
                        170400,
                        new User("Ichwan", "blablabla.jpg")
                )
        );

        assertNotNull(reviewList);
    }

}
