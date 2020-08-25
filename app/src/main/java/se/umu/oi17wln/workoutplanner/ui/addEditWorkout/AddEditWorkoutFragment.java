package se.umu.oi17wln.workoutplanner.ui.addEditWorkout;

import androidx.lifecycle.ViewModelProvider;

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
 * Fragment used for add/edit Exercise entries to/in the database.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class AddEditWorkoutFragment extends Fragment {
    public static final String KEY_ID = "se.umu.oi17wln.workoutplanner.ui.addEditWorkout.ID";
    public static final String KEY_NAME = "se.umu.oi17wln.workoutplanner.ui.addEditWorkout.NAME";
    public static final String KEY_SETS = "se.umu.oi17wln.workoutplanner.ui.addEditWorkout.SETS";
    public static final String KEY_REPS = "se.umu.oi17wln.workoutplanner.ui.addEditWorkout.REPS";

    private View fragmentView;
    private AddEditWorkoutViewModel addEditWorkoutViewModel;
    private EditText exerciseEditText;
    private EditText setsEditText;
    private EditText repsEditText;

    /**
     * Get instance for adding new workout
     * @return = new instance
     */
    public static AddEditWorkoutFragment getAddInstance(){
        return new AddEditWorkoutFragment();
    }


    /**
     * Get instance for editing an existing workout
     * @param entity = db entry to edit
     * @return = new instance with given bundle
     */
    public static AddEditWorkoutFragment getEditInstance(ExerciseEntity entity)
    {
        AddEditWorkoutFragment fragment = new AddEditWorkoutFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, entity.getId());
        bundle.putString(KEY_NAME, entity.getName());
        bundle.putInt(KEY_SETS, entity.getSets());
        bundle.putInt(KEY_REPS, entity.getReps());
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Runs when the Fragment is created. Setup and inflate the
     * fragment and its view components.
     * @param inflater = view inflater
     * @param container = not used
     * @param savedInstanceState = saved state
     * @return = the fragment itself
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.add_workout_fragment, container, false);
        addEditWorkoutViewModel = new ViewModelProvider(requireActivity()).get(AddEditWorkoutViewModel.class);


        ((MainActivity) requireActivity()).hideBottomNavigationView();
        setHasOptionsMenu(true);
        setupEditTextsInstances();

        setUpForEditWorkout();

        return fragmentView;
    }

    /**
     * Setup Edit Text user inputs
     */
    private void setupEditTextsInstances() {
        exerciseEditText = fragmentView.findViewById(R.id.user_input_exercise);
        setsEditText = fragmentView.findViewById(R.id.user_input_sets);
        repsEditText = fragmentView.findViewById(R.id.user_input_reps);
    }


    /**
     * Setup pre-entered data for Editing an existing db entry
     */
    private void setUpForEditWorkout() {
        if (getArguments() != null) { // not an "Add workout" instance
            Bundle bundle = getArguments();
            addEditWorkoutViewModel.setEntityID(bundle.getInt(KEY_ID));
            exerciseEditText.setText(bundle.getString(KEY_NAME));
            setsEditText.setText(Integer.toString(bundle.getInt(KEY_SETS)));
            repsEditText.setText(Integer.toString(bundle.getInt(KEY_REPS)));
        }
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
     *
     * Called after fragment onCreateView!!
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: restore state here
    }


    /**
     * Inflate the fragment options menu
     * @param menu = the menu
     * @param inflater = menu inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Save the data when the fragment menu item is clicked.
     * If data isn't saved, do nothing.
     *
     * @param item = item that was clicked
     * @return = true if action succeeded.
     */
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


    /**
     * Save the input data from EditText fields to db if all fields are filled.
     * If not, display error message prompting all fields to be filled.
     *
     * @return = true if saved, false if not saved.
     */
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

            if (addEditWorkoutViewModel.getEntityID() == -1) {
                addEditWorkoutViewModel.insert(dbEntry);
            } else {
                dbEntry.setId(addEditWorkoutViewModel.getEntityID()); // NOTE: important !!!
                addEditWorkoutViewModel.update(dbEntry);
            }

            return true;
        }
    }


    /**
     * View is to be destroyed. Show navigation again and reset
     * ViewModel ID value.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (((MainActivity) requireActivity()).getBottomNavigationViewVisibility() == View.GONE){
            ((MainActivity) requireActivity()).showBottomNavigationView();
        }
        addEditWorkoutViewModel.setEntityID(-1);
    }
}