package se.umu.oi17wln.workoutplanner.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import se.umu.oi17wln.workoutplanner.model.Util;
import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityEntity;
import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityRepository;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;
import se.umu.oi17wln.workoutplanner.model.person.PersonRepository;

/**
 * ViewModel for the HomeFragment / landing page.
 * Acts as a middle man between a View and the underlying
 * business logic / model classes.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class HomeViewModel extends AndroidViewModel {
    private PersonRepository personRepo;
    private LiveData<PersonEntity> personInfo;

    /**
     * Constructor, setup repositories for db access and LiveData
     * @param app = for context
     */
    public HomeViewModel(@NonNull Application app) {
        super(app);
        //dailyActivityRepo = new DailyActivityRepository(app);
        personRepo = new PersonRepository(app);
        personInfo = personRepo.getLatestEntry();
    }


    /**
     * Get the latest person information
     * @return = PersonEntity
     */
    public LiveData<PersonEntity> getLatestPersonInfo() {
        return personInfo;
    }
}