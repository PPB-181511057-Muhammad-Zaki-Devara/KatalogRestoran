package com.deva.katalogrestoran.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deva.katalogrestoran.R;
import com.deva.katalogrestoran.adapter.ReviewAdapter;
import com.deva.katalogrestoran.mapview.CustomMapView;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.reviews.Review;
import com.deva.katalogrestoran.task.LoadImageUrl;
import com.deva.katalogrestoran.viewmodel.RestaurantDetailsViewModel;

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

public class RestaurantDetailActivity extends AppCompatActivity {
    private CustomMapView mMapView;
    private Context context;
    private RestaurantDetailsViewModel mRestaurantDetailsViewModel;

    private TextView restaurantName;
    private TextView costForTwo;
    private TextView currency;
    private TextView rating;
    private RatingBar ratingStars;
    private TextView hasOnlineDelivery;
    private ImageView restaurantPhoto;
    private RecyclerView reviewsRecyclerView;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        this.context = getApplicationContext();

        // Cari elemen-elemen UI dari layout
        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        costForTwo = (TextView) findViewById(R.id.cost_for_two);
        currency = (TextView) findViewById(R.id.currency);
        rating = (TextView) findViewById(R.id.rating);
        ratingStars = (RatingBar) findViewById(R.id.rating_stars);
        hasOnlineDelivery = (TextView) findViewById(R.id.has_online_delivery);
        restaurantPhoto = (ImageView) findViewById(R.id.restaurant_photo);
        reviewsRecyclerView = (RecyclerView) findViewById(R.id.reviews_recycle_view);

        // Get restaurant id from intent
        Intent iGet = getIntent();
        long resId = iGet.getLongExtra("resId", 0);

        //Inisiasi ViewModel
        mRestaurantDetailsViewModel = ViewModelProviders.of(this).get(RestaurantDetailsViewModel.class);
        mRestaurantDetailsViewModel.getListOfReviews(resId);
        mRestaurantDetailsViewModel.getListOfReviews(resId).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                mReviewAdapter.setDataset(reviews);
            }
        });
        mRestaurantDetailsViewModel.getRestaurant(resId).observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                //Render UI
                renderRestaurantDetails(restaurant);
                addItemOverLay(restaurant);
            }
        });

        // Konfigurasi MapView
        Configuration.getInstance().setUserAgentValue("com-deva-katalogrestoran");
        mMapView = (CustomMapView) findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setTilesScaledToDpi(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

        //setup recycler view
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (mReviewAdapter == null) {
            mReviewAdapter = new ReviewAdapter(this);
            reviewsRecyclerView.setHasFixedSize(true);
            reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            reviewsRecyclerView.setAdapter(mReviewAdapter);
            reviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            reviewsRecyclerView.setNestedScrollingEnabled(true);
        } else {
            mReviewAdapter.notifyDataSetChanged();
        }
    }
    
    public void renderRestaurantDetails(Restaurant mRestaurant){
        if(mRestaurant != null){
            restaurantName.setText(mRestaurant.getName());
            costForTwo.setText(Integer.toString(mRestaurant.getAverageCostForTwo()));
            currency.setText(mRestaurant.getCurrency());
            rating.setText(Float.toString(mRestaurant.getUserRating().getAggregateRating()));
            ratingStars.setRating(mRestaurant.getUserRating().getAggregateRating());
            String isDeliveringStatus = mRestaurant.getHasOnlineDelivery() == 0 ? "Online Order Not Available" : "Online Order Available";
            hasOnlineDelivery.setText(isDeliveringStatus);
            restaurantPhoto.setAdjustViewBounds(true);
            new LoadImageUrl(restaurantPhoto).execute(mRestaurant.getFeaturedImageUrl());

            //Set fokus dan zoom level map
            IMapController mMapViewController = mMapView.getController();
            mMapViewController.setZoom(18);
            mMapViewController.setCenter(mRestaurant.getRestaurantGeoPoint());
        }
    }


    /* Menambahkan overlay yang berupa icon untuk menunjukkan lokasi restoran */
    public void addItemOverLay(Restaurant mRestaurant){
        if(mRestaurant != null){
            // Kumpulan markers yang akan ditambahkan pada itemized overlay
            ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
            items.add(new OverlayItem(mRestaurant.getName(), mRestaurant.getLocation().getAddress(), mRestaurant.getRestaurantGeoPoint())); // Lat/Lon decimal degrees

            // menginstansiasikan overlay
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
