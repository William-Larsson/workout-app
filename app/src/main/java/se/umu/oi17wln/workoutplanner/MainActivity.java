package se.umu.oi17wln.workoutplanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import se.umu.oi17wln.workoutplanner.ui.home.HomeFragment;
import se.umu.oi17wln.workoutplanner.ui.notifications.NotificationsFragment;
import se.umu.oi17wln.workoutplanner.ui.profile.ProfileFragment;

/**
 * Main Activity for hosting the bottom navigation bar,
 * as well as the main content fragments.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG_EDIT_PERSON_INFO = "se.umu.oi17wln.EDIT_PERSON_INFO";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpBottomNavigation();
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }


    /**
     * Adds  Bottom App Bar navigation to different fragment-pages of the app
     */
    private void  setUpBottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener((menuItem) -> {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new NotificationsFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, Objects.requireNonNull(selectedFragment))
                    .commit();

            return true;
        });
    }


    /**
     * Hide the bottom navigation bar with an animation
     */
    public void hideBottomNavigationView(){
        bottomNavigationView
                .animate()
                .translationY(bottomNavigationView.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        bottomNavigationView.setVisibility(View.GONE);
                    }
                });
    }


    /**
     * Show the bottom navigation bar with an animation
     */
    public void showBottomNavigationView(){
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView
                .animate()
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });
    }


    /**
     * Get current bottom navigation bar visibility status
     * @return = View.VISIBLE / View.GONE
     */
    public int getBottomNavigationViewVisibility(){
        return bottomNavigationView.getVisibility();
    }












    // TODO: this is pseudo code for the step counter:
    /*
     * OBS! Step sensor will count steps from the LAST BOOT. The counter does not reset
     * until the phone is rebooted again, and cannot be resetted any other way.
     *
     * SensorManager = new Sensor(Step_sensor) <-- gets the step counter
     *
     * implement sensorEventListener <-- do this wherever you want to init this, probably in a server that is always runnging
     *                                   onSensorChanged --> increment the number of steps in SharedPrefs or Database.
     *                                   Every hit of onSensorChanged is a quaranteed step, no duplicates.
     *
     * place the above code in a Service (probably)
     * start the service on entering an activity of your choice for use in updating the UI of that Activity
     *
     *
     * Most reliable implementation on Github --> j4velin/Pedomenter
     *
     * in Manifest.xml:
     *
     * <uses-feature
     *      android:name:"android.hardware.sensor.stepcounter"
     *      android:required="true"/>
     *
     * <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"
     *
     * This makes the sensor required. Maybe this isn't the best fit for my app?
     * RECEIVE_BOOT_COMPLETED let the service that right after boot, even if the app isn't launched.
     *
     *
     *
     */
}