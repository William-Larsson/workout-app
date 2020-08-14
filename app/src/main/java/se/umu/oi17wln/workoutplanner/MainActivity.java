package se.umu.oi17wln.workoutplanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * Main Activity for hosting the bottom navigation bar,
 * as well as the main content fragments.
 */
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpBottomNavigation();
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
    }


    /**
     * Adds  Bottom App Bar navigation to different fragment-pages of the app
     */
    private void  setUpBottomNavigation(){
        bottomNavigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
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