package se.umu.oi17wln.workoutplanner;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * Main Activity for hosting the bottom navigation bar,
 * as well as the main content fragments.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpBottomNavigation();
    }


    /**
     * Adds  Bottom App Bar navigation to different fragments of the app
     */
    private void  setUpBottomNavigation(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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