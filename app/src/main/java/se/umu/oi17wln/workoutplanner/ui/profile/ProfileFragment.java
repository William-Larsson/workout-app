package se.umu.oi17wln.workoutplanner.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import se.umu.oi17wln.workoutplanner.R;

/**
 * View class for handling immediate actions related
 * to the GUI-component of this fragment. Updates with components
 * from ViewModel data and executes Android specific
 * operations related to the GUI.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;


    /**
     * Constructor, sets up ViewModel and Views
     * @param inflater = Fragment inflater
     * @param container = not used
     * @param savedInstanceState = saved state
     * @return = the fragment itseld
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}