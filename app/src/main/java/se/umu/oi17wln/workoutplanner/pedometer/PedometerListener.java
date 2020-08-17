package se.umu.oi17wln.workoutplanner.pedometer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * A services that listens to the hardware pedometer and calculated the
 * number of steps the user has taken.
 */
public class PedometerListener extends Service implements SensorEventListener {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called when the service is first started.
     * @param intent =
     * @param flags Can be 0, START_FLAG_REDELIVERY or START_FLAG_RETRY
     *              START_FLAG_RETRY can indicate a bug, because previous
     *              onStartCommand never returned before it was stopped.
     * @param startId start request ID
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE", "PedometerListener service started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
