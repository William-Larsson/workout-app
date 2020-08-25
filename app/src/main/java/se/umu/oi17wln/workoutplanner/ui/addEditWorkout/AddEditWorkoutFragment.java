package se.umu.oi17wln.workoutplanner.ui.addWorkout;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import se.umu.oi17wln.workoutplanner.MainActivity;
import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;

/**
 *
 */
public class AddEditWorkoutFragment extends Fragment {

    private View fragmentView;
    private AddWorkoutViewModel addWorkoutViewModel;
    private EditText exerciseEditText;
    private EditText setsEditText;
    private EditText repsEditText;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.add_workout_fragment, container, false);
        addWorkoutViewModel = new ViewModelProvider(requireActivity()).get(AddWorkoutViewModel.class);


        ((MainActivity) requireActivity()).hideBottomNavigationView();
        setHasOptionsMenu(true);
        setupEditTexts();

        return fragmentView;
    }

    /**
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO: save state here
    }


    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: restore state here
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_menu_item) {
            boolean dataSaved = saveData();
            if (dataSaved) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private boolean saveData(){
        String exerciseName = exerciseEditText.getText().toString();
        String nrOfSets = setsEditText.getText().toString();
        String nrOfReps = repsEditText.getText().toString();

        if (exerciseName.trim().isEmpty() ||
                nrOfSets.trim().isEmpty() ||
                nrOfReps.trim().isEmpty()) {
            Toast.makeText(
                    requireContext(),
                    "Please fill in all fields above",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        } else {
            int sets = Integer.parseInt(nrOfSets);
            int reps = Integer.parseInt(nrOfReps);

            ExerciseEntity dbEntry = new ExerciseEntity(exerciseName, sets, reps);
            addWorkoutViewModel.insert(dbEntry);

            return true;
        }
    }


    /**
     *
     */
    private void setupEditTexts() {
        exerciseEditText = fragmentView.findViewById(R.id.user_input_exercise);
        setsEditText = fragmentView.findViewById(R.id.user_input_sets);
        repsEditText = fragmentView.findViewById(R.id.user_input_reps);
    }


    /**
     * View is to be destroyed. Show navigation again.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (((MainActivity) requireActivity()).getBottomNavigationViewVisibility() == View.GONE){
            ((MainActivity) requireActivity()).showBottomNavigationView();
        }
    }
}