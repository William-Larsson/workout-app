package se.umu.oi17wln.workoutplanner.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<Integer> totalSteps;
    private int previousStepsSinceBoot;

    /**
     * Constructor, setup repositories for db access and LiveData
     * @param app = for context
     */
    public HomeViewModel(@NonNull Application app) {
        super(app);
        //dailyActivityRepo = new DailyActivityRepository(app);
        personRepo = new PersonRepository(app);
        personInfo = personRepo.getLatestEntry();
        totalSteps = new MutableLiveData<>();

    }


    /**
     * Sets previous steps taken from sensor
     * @param steps = nr of steps taken
     */
    public void setPreviousStepsSinceBoot(int steps) {
        previousStepsSinceBoot = steps;
    }


    /**
     * Calculate the total of steps taken since app start
     * @param currentNrOfSteps = updated total of steps since boot
     */
    public void calculateTotalSteps(int currentNrOfSteps) {
        int previousSteps = totalSteps.getValue() != null ? totalSteps.getValue() : 0;
        int newTotal = currentNrOfSteps - previousStepsSinceBoot + previousSteps;
        totalSteps.setValue(newTotal);
    }


    /**
     * Get the latest person information
     * @return = PersonEntity
     */
    public LiveData<PersonEntity> getLatestPersonInfo() {
        return personInfo;
    }


    /**
     * Get the latest step count
     * @return = integer
     */
    public LiveData<Integer> getCurrentTotalSteps() {
        return totalSteps;
    }

}