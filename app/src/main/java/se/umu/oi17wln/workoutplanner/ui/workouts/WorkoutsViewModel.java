package se.umu.oi17wln.workoutplanner.ui.workouts;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseRepository;


/**
 * ViewModel for the WorkoutsFragment.
 * Acts as a middle man between a View and the underlying
 * business logic / model classes.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class WorkoutsViewModel extends AndroidViewModel {
    private ExerciseRepository repo;
    private LiveData<List<ExerciseEntity>> allExercises;

    /**
     * Constructor. Set up repo and LiveData
     * @param app = for context
     */
    public WorkoutsViewModel(@NonNull Application app) {
        super(app);
        repo = new ExerciseRepository(app);
        allExercises = repo.getAllExercises();
    }


    /**
     * Delete given Entity from database
     * @param exerciseEntity = to be deleted
     */
    public void delete(ExerciseEntity exerciseEntity){
        repo.delete(exerciseEntity);
    }


    /**
     * Delete all rows from db table
     */
    public void deleteAll(){
        repo.deleteAll();
    }


    /**
     * LiveData observable, for automatically updating
     * list of exercises
     * @return = LiveData
     */
    public LiveData<List<ExerciseEntity>> getAllExercises(){
        return allExercises;
    }


}

