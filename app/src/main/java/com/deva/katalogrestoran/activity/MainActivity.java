package com.deva.katalogrestoran.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deva.katalogrestoran.Config;
import com.deva.katalogrestoran.R;
import com.deva.katalogrestoran.adapter.RestaurantAdapter;
import com.deva.katalogrestoran.model.restaurants.Restaurant;
import com.deva.katalogrestoran.model.search.SearchRestaurantResponse;
import com.deva.katalogrestoran.rest.API;
import com.deva.katalogrestoran.viewmodel.RestaurantViewModel;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HEAD;

public class MainActivity extends AppCompatActivity{
    private RecyclerView restaurantRecyclerView;
    private RestaurantAdapter mAdapter;
    private RestaurantViewModel mRestaurantViewModel;
    private final int PERMISSION_REQUEST_CODE = 100;
    private EditText kolom_search;
    private Button tombol_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Meminta Request untuk permission yang dibutuhkan jika belum diberi
        requestPermissionsIfNeeded(new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        });

        //Cari recycler view di dalam layout
        restaurantRecyclerView = (RecyclerView) findViewById(R.id.restaurant_recycler_view);

        //Cari Button dan Kolom untuk pencarian
        tombol_search = (Button) findViewById(R.id.Btn_Search);
        kolom_search = (EditText) findViewById(R.id.EdtTxt_Search);

        //Inisiasi ViewModel
        mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        mRestaurantViewModel.searchRestaurants("", 11052, "city", "rating");
        mRestaurantViewModel.getListOfRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                mAdapter.setDataset(restaurants);
            }
        });

        // Setup Recycler View
        setupRecyclerView();
    }

    public void search_menu(View view) {
        String searchValue = kolom_search.getText().toString();
        mRestaurantViewModel.searchRestaurants(searchValue, 11052, "city", "rating");
    }


    /* Recycler Touch Listener */
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    private void setupRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new RestaurantAdapter(MainActivity.this);
            restaurantRecyclerView.setHasFixedSize(true);
            restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            restaurantRecyclerView.setAdapter(mAdapter);
            restaurantRecyclerView.setItemAnimator(new DefaultItemAnimator());
            restaurantRecyclerView.setNestedScrollingEnabled(true);
            restaurantRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), restaurantRecyclerView, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), RestaurantDetailActivity.class);
                            intent.putExtra("resId", mRestaurantViewModel.getListOfRestaurants().getValue().get(position).getId());
                            startActivity(intent);
                        }
                    }, 400);
//                Toast.makeText(getApplicationContext(), "Item " + position + " clicked", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
    
    public interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    /* PERMISSIONS */
    private boolean checkPermissions(String[] permissions){
        for (String s:
                permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), s) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionsIfNeeded(String[] permissions){
        if(!checkPermissions(permissions)){
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResults(int requestCode, String[] permissions,
                                            int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
    }

}