package com.deva.katalogrestoran.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deva.katalogrestoran.Config;
import com.deva.katalogrestoran.R;
import com.deva.katalogrestoran.adapter.ReviewAdapter;
import com.deva.katalogrestoran.mapview.CustomMapView;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.reviews.RestaurantReviewsResponse;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.rest.API;
import com.deva.katalogrestoran.task.LoadImageUrl;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RestaurantDetailActivity extends AppCompatActivity {
    private CustomMapView mMapView;
    private Context context;
    private Restaurant mRestaurant;
    private List<Review> mReviews;


    private TextView restaurantName;
    private TextView costForTwo;
    private TextView currency;
    private TextView rating;
    private RatingBar ratingStars;
    private TextView hasOnlineDelivery;
    private ImageView restaurantPhoto;
    private RecyclerView reviewsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        this.context = getApplicationContext();

        // View Detail Restoran
        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        costForTwo = (TextView) findViewById(R.id.cost_for_two);
        currency = (TextView) findViewById(R.id.currency);
        rating = (TextView) findViewById(R.id.rating);
        ratingStars = (RatingBar) findViewById(R.id.rating_stars);
        hasOnlineDelivery = (TextView) findViewById(R.id.has_online_delivery);
        restaurantPhoto = (ImageView) findViewById(R.id.restaurant_photo);
        reviewsRecyclerView = (RecyclerView) findViewById(R.id.reviews_recycle_view);
        reviewsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(layoutManager);


        // Map
        Configuration.getInstance().setUserAgentValue("com-deva-katalogrestoran");
        mMapView = (CustomMapView) findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setTilesScaledToDpi(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

        // Get restaurant id from intent
        Intent iGet = getIntent();
        long resId = iGet.getLongExtra("resId", 0);


        API.restaurants().restaurantDetails(Config.API_KEY, resId).enqueue(new retrofit2.Callback<Restaurant>(){

            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                mRestaurant = response.body();
                render();
                IMapController mMapViewController = mMapView.getController();
                mMapViewController.setZoom(18);
                mMapViewController.setCenter(mRestaurant.getRestaurantGeoPoint());
                addItemOverLay();
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e("RestaurantDetailActivity", t.toString());
                t.printStackTrace();
            }
        });

        API.restaurants().restaurantReviews(Config.API_KEY, resId).enqueue(new retrofit2.Callback<RestaurantReviewsResponse>(){

            @Override
            public void onResponse(Call<RestaurantReviewsResponse> call, Response<RestaurantReviewsResponse> response) {
                if(response.body().getReviewsCount() > 0){
                    mReviews = response.body().getReviews();
                    reviewsRecyclerView.setAdapter(new ReviewAdapter(mReviews));
                }else{
                }
            }

            @Override
            public void onFailure(Call<RestaurantReviewsResponse> call, Throwable t) {
                Log.e("RestaurantDetailActivity", t.toString());
                t.printStackTrace();
            }
        });
    }

    public void render(){
        restaurantName.setText(mRestaurant.getName());
        costForTwo.setText(Integer.toString(mRestaurant.getAverageCostForTwo()));
        currency.setText(mRestaurant.getCurrency());
        rating.setText(Float.toString(mRestaurant.getUserRating().getAggregateRating()));
        ratingStars.setRating(mRestaurant.getUserRating().getAggregateRating());
        String isDeliveringStatus = mRestaurant.getHasOnlineDelivery() == 0 ? "Online Order Not Available" : "Online Order Available";
        hasOnlineDelivery.setText(isDeliveringStatus);
        restaurantPhoto.setAdjustViewBounds(true);
        new LoadImageUrl(restaurantPhoto).execute(mRestaurant.getFeaturedImageUrl());
    }


    public void addItemOverLay(){
        //your items
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem(mRestaurant.getName(), mRestaurant.getLocation().getAddress(), mRestaurant.getRestaurantGeoPoint())); // Lat/Lon decimal degrees

        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, context);
        mOverlay.setFocusItemsOnTap(true);
        mMapView.getOverlays().add(mOverlay);
    }

    public void addMapEventOverlay(){
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = (ItemizedOverlayWithFocus<OverlayItem>) mMapView.getOverlays().get(0);
        final MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if(!mOverlay.getFocusedItem().getPoint().equals(p)){
                    mOverlay.unSetFocusedItem();
                    return true;
                }
                return false;
            }

            @Override
            public boolean longPressHelper(final GeoPoint p) {return false;}
        };
        mMapView.getOverlays().add(new MapEventsOverlay(mReceive));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
