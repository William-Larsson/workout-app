package se.umu.oi17wln.workoutplanner.ui.workouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import se.umu.oi17wln.workoutplanner.MainActivity;
import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;
import se.umu.oi17wln.workoutplanner.ui.addEditWorkout.AddEditWorkoutFragment;
import se.umu.oi17wln.workoutplanner.ui.addEditWorkout.AddEditWorkoutViewModel;

/**
 * View class for handling immediate actions related
 * to the GUI-component of this fragment. Updates with components
 * from ViewModel data and executes Android specific
 * operations related to the GUI.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class WorkoutsFragment extends Fragment {

    private View fragmentView;
    private RecyclerView recyclerView;
    private WorkoutsViewModel workoutsViewModel;
    private WorkoutsAdapter recyclerViewAdapter;

    /**
     * View is created, inflate the fragment, set up ViewModel
     * and Views.
     * @param inflater = fragment inflater
     * @param container = not used
     * @param savedInstanceState = save state
     * @return = the fragment itself
     */
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.fragment_workouts, container, false);
        workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);
        recyclerViewAdapter = new WorkoutsAdapter();

        setupRecyclerView();
        setupItemSwipeToDelete();
        setupExerciseMenuListener();

        workoutsViewModel.getAllExercises().observe(getViewLifecycleOwner(), this::updateRecyclerView);

        setupAddWorkoutButton();
        setHasOptionsMenu(true);

        return fragmentView;
    }


    /**
     * Set up the Recycler view with custom adapter
     */
    private void setupRecyclerView() {
        recyclerViewAdapter = new WorkoutsAdapter();
        recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    /**
     * Setup for allowing swiping left or right on
     * a RecyclerView item to delete.
     */
    private void setupItemSwipeToDelete(){
        // 0 because no drag and drop functionality
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target)
            {
                // not used
                return false;
            }

            /**
             * Deletes exercise that gets wiped away
             * @param viewHolder = the swiped view holder
             * @param direction = swipe direction
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                workoutsViewModel.delete(
                        recyclerViewAdapter.getExerciseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(requireContext(), "Exercise deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }


    /**
     * Menu listener for the recycler view items.
     * Show pop up menu on press and start AddEditWorkoutFragment if
     * menu item is pressed
     */
    private void setupExerciseMenuListener(){
        recyclerViewAdapter.setOnMenuButtonClickListener(((exerciseView, exerciseEntity) ->  {
            PopupMenu popup = new PopupMenu(getContext(), exerciseView);
            popup.inflate(R.menu.exercise_options_menu);

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.edit_exercise:
                        startAddEditWorkoutFragment(
                                AddEditWorkoutFragment.getEditInstance(exerciseEntity));
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        }));
    }


    /**
     * Update the list by calling submit list, which
     * tells the adapter that the list has changed.
     * @param exerciseEntities = new list
     */
    private void updateRecyclerView(List<ExerciseEntity> exerciseEntities) {
        recyclerViewAdapter.submitList(exerciseEntities);
    }


    /**
     * Setup for the floating action button.
     * If pressed, start AddEditWorkoutFragment to add new exercise
     */
    private void setupAddWorkoutButton() {
        FloatingActionButton addWorkoutBtn = fragmentView.findViewById(R.id.add_exercise_btn);
        addWorkoutBtn.setOnClickListener(view -> {
            startAddEditWorkoutFragment(AddEditWorkoutFragment.getAddInstance());
        });
    }


    /**
     * Open fragment for adding/editing a workout
     */
    private void startAddEditWorkoutFragment(AddEditWorkoutFragment fragmentToStart) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        ((ViewGroup) requireView().getParent()).getId(),
                        fragmentToStart,
                        MainActivity.TAG_ADD_WORKOUT
                )
                .addToBackStack("AddEditWorkoutBackStack")
                .commit();
    }


    /**
     * Create the fragment app bar menu
     * @param menu = the menu
     * @param inflater = menu inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.workouts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * If fragment menu item is pressed, delete all exercises
     * @param item = pressed menu item
     * @return = true if successful
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            workoutsViewModel.deleteAll();
            Toast.makeText(requireContext(), "All exercises deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}