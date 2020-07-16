package com.deva.katalogrestoran;

import android.widget.LinearLayout;
import android.widget.ScrollView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.deva.katalogrestoran.activity.MainActivity;
import com.deva.katalogrestoran.activity.RestaurantDetailActivity;
import com.deva.katalogrestoran.adapter.RestaurantAdapter;
import com.deva.katalogrestoran.matcher.RecyclerViewMatcher;
import com.deva.katalogrestoran.model.location.Location;
import com.deva.katalogrestoran.model.rating.UserRating;
import com.deva.katalogrestoran.model.restaurants.Restaurant;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    private int SELECTED_RESTAURANT_POSITION = 0;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp(){
    }

    /** Test RecyclerView ditampilkan pada saat MainActivity Dilaunch */
    @Test
    public void isRecyclerViewVisible(){
        onView(withId(R.id.restaurant_recycler_view)).check(matches(isDisplayed()));
    }

    /** Test konten ViewHolder di RecyclerView menampilkan semua informasi yang sesuai dengan requirements
     * expected: menampilkan informasi nama restoran, gambar, harga rata-rata, currency, rating, dan flag ketersediaan online order
     * */
    @Test
    public void displayRestaurantInformation() {
        onView(new RecyclerViewMatcher(R.id.restaurant_recycler_view).atPositionOnView(SELECTED_RESTAURANT_POSITION, R.id.restaurant_name)).check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.restaurant_recycler_view).atPositionOnView(SELECTED_RESTAURANT_POSITION, R.id.restaurant_photo)).check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.restaurant_recycler_view).atPositionOnView(SELECTED_RESTAURANT_POSITION, R.id.rating)).check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.restaurant_recycler_view).atPositionOnView(SELECTED_RESTAURANT_POSITION, R.id.has_online_delivery)).check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.restaurant_recycler_view).atPositionOnView(SELECTED_RESTAURANT_POSITION, R.id.cost_for_two)).check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.restaurant_recycler_view).atPositionOnView(SELECTED_RESTAURANT_POSITION, R.id.cost_for_two)).check(matches(isDisplayed()));
    }

    /** Test click pada item RecyclerView
     * expected: RestaurantDetailsActivity dimulai, dan menampilkan data nama restoran, gambar, harga rata-rata, currency, rating, flag ketersediaan online order
     *          mapview, dan reviews
     * */
    @Test
    public void clickRecyclerViewItem() throws InterruptedException {
        Thread.sleep(5000);
        onView(withId(R.id.restaurant_recycler_view))
                .perform(actionOnItemAtPosition(SELECTED_RESTAURANT_POSITION, click()));
        Thread.sleep(5000);
        onView(withId(R.id.restaurant_name_details)).check(matches(isDisplayed()));
        onView(withId(R.id.restaurant_photo_details)).check(matches(isDisplayed()));
        onView(withId(R.id.rating_details)).check(matches(isDisplayed()));
        onView(withId(R.id.currency_details)).check(matches(isDisplayed()));
        onView(withId(R.id.has_online_delivery_details)).check(matches(isDisplayed()));
        onView(withId(R.id.cost_for_two_details)).check(matches(isDisplayed()));

        onView(withId(R.id.cost_for_two_details)).perform(swipeUp());
        onView(withId(R.id.cost_for_two_details)).perform(swipeUp());
        onView(withId(R.id.mapview))
                .check(matches(isDisplayed()));
        onView(instanceOf(NestedScrollView.class)).check(matches(hasDescendant(withId(R.id.reviews_recycle_view))));
    }
}
