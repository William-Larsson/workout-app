package se.umu.oi17wln.workoutplanner.ui.workouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;
import se.umu.oi17wln.workoutplanner.ui.addWorkout.AddWorkoutViewModel;

/**
 *
 */
public class WorkoutsFragment extends Fragment {

    private View fragmentView;
    private AddWorkoutViewModel addWorkoutViewModel;
    private WorkoutsViewModel workoutsViewModel;
    private WorkoutsAdapter recyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.fragment_workouts, container, false);
        workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);
        addWorkoutViewModel = new ViewModelProvider(requireActivity()).get(AddWorkoutViewModel.class);
        recyclerViewAdapter = new WorkoutsAdapter();

        setupRecyclerView();
        addWorkoutViewModel.getAllExercises().observe(getViewLifecycleOwner(), this::updateRecyclerView);

        return fragmentView;
    }


    /**
     *
     */
    private void setupRecyclerView(){
        recyclerViewAdapter = new WorkoutsAdapter();
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    /**
     *
     * @param exerciseEntities
     */
    private void updateRecyclerView(List<ExerciseEntity> exerciseEntities) {
        recyclerViewAdapter.setExercises(exerciseEntities);
    }

}