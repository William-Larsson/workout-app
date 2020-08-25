package se.umu.oi17wln.workoutplanner.ui.addWorkout;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseRepository;

public class AddWorkoutViewModel extends AndroidViewModel {
    private ExerciseRepository repo;
    private LiveData<List<ExerciseEntity>> allExercises;

    public AddWorkoutViewModel(@NonNull Application app) {
        super(app);
        repo = new ExerciseRepository(app);
        allExercises = repo.getAllExercises();
    }


    public void insert(ExerciseEntity exerciseEntity){
        repo.insert(exerciseEntity);
    }


    public void update(ExerciseEntity exerciseEntity){
        repo.update(exerciseEntity);
    }


    public LiveData<List<ExerciseEntity>> getAllExercises(){
        return allExercises;
    }
}