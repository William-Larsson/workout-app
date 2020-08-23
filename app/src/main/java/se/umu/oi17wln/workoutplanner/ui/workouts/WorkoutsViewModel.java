package se.umu.oi17wln.workoutplanner.ui.workouts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseRepository;

public class WorkoutsViewModel extends AndroidViewModel {

    public WorkoutsViewModel(@NonNull Application app) {
        super(app);
    }
}

