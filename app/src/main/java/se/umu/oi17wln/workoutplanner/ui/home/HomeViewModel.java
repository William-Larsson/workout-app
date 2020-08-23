package se.umu.oi17wln.workoutplanner.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import se.umu.oi17wln.workoutplanner.model.Util;
import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityEntity;
import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityRepository;

/**
 * ViewModel for the HomeFragment / landing page.
 * Acts as a middle man between a View and the underlying
 * business logic / model classes.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class HomeViewModel extends AndroidViewModel {
    private DailyActivityRepository dailyActivityRepo;
    private LiveData<DailyActivityEntity> currentDailyActivity;

    public HomeViewModel(@NonNull Application app) {
        super(app);
        dailyActivityRepo = new DailyActivityRepository(app);
        currentDailyActivity = dailyActivityRepo.getEntryByDate(Util.getCurrentDate());
    }


    public void setCurrentDailyActivity(String date) {
        currentDailyActivity = dailyActivityRepo.getEntryByDate(date);
    }
}