package se.umu.oi17wln.workoutplanner.ui.addEditWorkout;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseRepository;


/**
 * ViewModel for the AddEditWorkoutFragment. Implement methods from
 * the exercise repository to allow for adding and editing rows to
 * the database.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class AddEditWorkoutViewModel extends AndroidViewModel {
    private ExerciseRepository repo;
    private LiveData<List<ExerciseEntity>> allExercises;
    private int entityID;


    /**
     * Constructor. Init the repo and default ID.
     * @param app = application for context
     */
    public AddEditWorkoutViewModel(@NonNull Application app) {
        super(app);
        repo = new ExerciseRepository(app);
        entityID = -1; // because no entity will ever have id = -1
    }


    /**
     * Insert ExerciseEntity to db through repository
     * @param exerciseEntity = entry to insert
     */
    public void insert(ExerciseEntity exerciseEntity){
        repo.insert(exerciseEntity);
    }


    /**
     * Update ExerciseEntity to db through repository
     * @param exerciseEntity = entry to update
     */
    public void update(ExerciseEntity exerciseEntity){
        repo.update(exerciseEntity);
    }


    public int getEntityID() {
        return entityID;
    }


    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }
}