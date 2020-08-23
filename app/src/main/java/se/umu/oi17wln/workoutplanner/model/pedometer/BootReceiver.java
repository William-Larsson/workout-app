package se.umu.oi17wln.workoutplanner.model.pedometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * A class for starting the step counter service in the background
 * directly after the device has rebooted.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG_DEVICE_BOOT = "DEVICE_BOOT";
    private static final int ID_PEDOMETER_JOB = 123;

    /**
     * Start the service in the background.
     * @param context = current context
     * @param intent = not used.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SERVICE17", "Device has booted.");
        Toast.makeText(context, "Device has booted.", Toast.LENGTH_SHORT).show();

        // TODO: REMOVE THIS CLASS??

        /*
        Toast.makeText(context, "Workout Planner is ready!", Toast.LENGTH_SHORT).show();
        Intent pedometerIntent = new Intent(context, PedometerService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(pedometerIntent);
        } else context.startService(pedometerIntent);
        */
        // whats the difference between startService and startForegroundService?
        // should the latter be used instead?
    }
}