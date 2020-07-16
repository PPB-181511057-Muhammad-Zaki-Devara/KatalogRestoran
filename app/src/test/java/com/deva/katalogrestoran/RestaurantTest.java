package com.deva.katalogrestoran;


import com.deva.katalogrestoran.model.location.Location;
import com.deva.katalogrestoran.model.rating.UserRating;
import com.deva.katalogrestoran.model.restaurants.Restaurant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.osmdroid.util.GeoPoint;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
//Mengetes Method pada class Restaurant
public class RestaurantTest {

    private Restaurant restaurantNotNull;
    private Restaurant restaurantNull;

    @Before
    public void setup(){
        restaurantNotNull = new Restaurant(
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
        );

        restaurantNull = new Restaurant(
                        16774318,
                        "Martabak San Fransisco",
                        "https://b.zmtcdn.com/data/pictures/chains/8/16774318/a54deb9e4dbb79dd7c8091b30c642077_featured_thumb.png",
                        60,
                        2,
                        "$",
                        new UserRating(3.7f),
                        0,
                        0,
                        null
        );
    }

    @Test
    public void getRestaurantGeoPointNotNull(){
        GeoPoint resultGeoPointNotNull = restaurantNotNull.getRestaurantGeoPoint();
        assertThat(resultGeoPointNotNull, is(equalTo(new GeoPoint(40.732013, -73.996155))));
    }

    @Test
    public void getRestaurantGeoPointNull(){
        GeoPoint resultGeoPointNull = restaurantNull.getRestaurantGeoPoint();
        assertThat(resultGeoPointNull, is(equalTo(new GeoPoint(0, 0))));
    }
}
