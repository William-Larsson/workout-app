package se.umu.oi17wln.workoutplanner.ui.workouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import se.umu.oi17wln.workoutplanner.R;

public class WorkoutsFragment extends Fragment {

    private WorkoutsViewModel workoutsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);
        workoutsViewModel.getAllExercises().observe(getViewLifecycleOwner(), exerciseEntities -> {
            // update the recycler view
            Toast.makeText(requireContext(), "All exercises onChanged", Toast.LENGTH_SHORT).show();
        });

        View root = inflater.inflate(R.layout.fragment_workouts, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);

        return root;
    }
}