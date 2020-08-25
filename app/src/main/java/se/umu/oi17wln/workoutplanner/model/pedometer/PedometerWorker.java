package se.umu.oi17wln.workoutplanner.model.pedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Worker class for saving user steps to the database
 *
 * Author: William Larsson
 */
public class PedometerWorker extends Worker {
    private static final String TAG = "PedometerWorker";
    private boolean isStopped;

    /**
     * Trigger used to fake a step sensor call
     */
    private TriggerEventListener listener = new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {
            if (!isStopped) {
                // get sensor data
                float currentSteps = event.values[0];
                Log.d(TAG, "Steps from PedometerWorker! : " + currentSteps);
                // save to the database...
            }
        }
    };


    /**
     * Constructor
      * @param context = context
     * @param workerParams
     */
    public PedometerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    /**
     * Get the sensor data and save new steps to the database
     * @return = if the operation succeeded or failed
     */
    @NonNull
    @Override
    public Result doWork() {
        isStopped = false;

        SensorManager sensorManager =
                (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);

        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Log.d(TAG, "PedometerWorker: No step sensor found.");
            return Result.failure();
        } else {
            Log.d(TAG, "PedometerWorker: Step sensor found!");
            sensorManager.requestTriggerSensor(listener, stepSensor);
        }

        return Result.success();
    }


    /**
     * Worker has been cancelled, stop operation if possible
     */
    @Override
    public void onStopped() {
        super.onStopped();
        isStopped = true;
    }
}
