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
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import se.umu.oi17wln.workoutplanner.model.pedometer.PedometerWorker;
import se.umu.oi17wln.workoutplanner.ui.home.HomeFragment;
import se.umu.oi17wln.workoutplanner.ui.workouts.WorkoutsFragment;
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
    private static final String TAG_MAIN_ACTIVITY = "se.umu.oi17wln.MAIN_ACTIVITY";
    private static final String TAG_SAVE_REQUEST = "savePedometerRequest";
    private static final String TAG_SAVE_REQUEST_PERIODIC = "savePedometerRequestPeriodic";
    private static final String TAG_UNIQUE_WORKER = "savePedometerWorker";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cancelPedometerWorkers(); // cancel old worker(s) if they exist.
        //schedulePedometerWorkers(); // schedule new workers

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
                case R.id.navigation_workouts:
                    selectedFragment = new WorkoutsFragment();
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



    // ----------------- For the Pedometer Worker ----------------- //



    /**
     * Start the Pedometer Workers for updating the
     * users taken steps in the background.
     */
    public void schedulePedometerWorkers() {
        Log.d(TAG_MAIN_ACTIVITY, "Pedometer workers has been scheduled");
        startOneTimePedometerWorker();
        startPeriodicPedometerWorker();
    }


    /**
     * Cancel all active workers.
     */
    public void cancelPedometerWorkers() {
        Log.d(TAG_MAIN_ACTIVITY, "Pedometer worker has been cancelled.");
        WorkManager.getInstance(this).cancelAllWork();
    }


    /**
     * Start a pedometer worker that will be started relatively
     * immediately after getting enqueued, but only runs once.
     */
    private void startOneTimePedometerWorker(){
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(PedometerWorker.class)
                .addTag(TAG_SAVE_REQUEST)
                .build();

        WorkManager.getInstance(this).enqueue(workRequest);

        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, (workInfo) -> {
                    Log.d(TAG_MAIN_ACTIVITY,
                            "Pedometer One-Time worker status: " + workInfo.getState());
                });
    }


    /**
     * Start a periodic pedometer worker that will run
     * once every 20-30-ish minutes depending on when
     * Android system finds in the most appropriate.
     */
    private void startPeriodicPedometerWorker(){

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                PedometerWorker.class,
                20*60*1000L, TimeUnit.MILLISECONDS,
                10*60*1000L, TimeUnit.MILLISECONDS)
                .addTag(TAG_SAVE_REQUEST_PERIODIC)
                .setInitialDelay(10, TimeUnit.MILLISECONDS)
                .build();


        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(TAG_UNIQUE_WORKER,
                        ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);


        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, (workInfo) -> {
                    Log.d(TAG_MAIN_ACTIVITY,
                            "Pedometer Periodic worker status: " + workInfo.getState());
                });
    }
}