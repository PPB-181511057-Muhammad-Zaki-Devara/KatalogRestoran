package com.deva.katalogrestoran.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deva.katalogrestoran.Config;
import com.deva.katalogrestoran.R;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.rest.API;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RestaurantDetailActivity extends AppCompatActivity {
    private MapView mMapView;
    private Context context;
    private Restaurant mRestaurant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        this.context = getApplicationContext();

        Configuration.getInstance().setUserAgentValue("com-deva-katalogrestoran");
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setTilesScaledToDpi(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

        //Copyright overlay
//        String copyrightNotice = map.getTileProvider().getTileSource().getCopyrightNotice();
//        CopyrightOverlay copyrightOverlay = new CopyrightOverlay(this);
//        copyrightOverlay.setCopyrightNotice(copyrightNotice);
//        mMapView.getOverlays().add(copyrightOverlay);



        // Get restaurant id from intent
        Intent iGet = getIntent();
        long resId = iGet.getLongExtra("resId", 0);
        API.restaurants().restaurantDetails(Config.API_KEY, resId).enqueue(new retrofit2.Callback<Restaurant>(){

            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                mRestaurant = response.body();
                Log.d("restaurant", response.body().toString());
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
                        Toast.makeText(context, item.getTitle() + " " + Double.toString(mRestaurant.getLocation().getLatitude()) + ", " + Double.toString(mRestaurant.getLocation().getLongitude()), Toast.LENGTH_LONG).show();
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
