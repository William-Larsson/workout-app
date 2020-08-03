package se.umu.oi17wln.workoutplanner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;

/**
 * View class for handling immediate actions related
 * to the GUI-component of this fragment. Updates components
 * from ViewModel data and executes Android specific
 * operations related to the GUI.
 *
 * Author: William Larsson
 */
public class HomeFragment extends Fragment {

    private View fragmentView;
    private HomeViewModel homeViewModel;

    public View onCreateView (
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState )
    {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getPersonInfo().observe(getViewLifecycleOwner(), this::updatePersonInfoView);

        return fragmentView;
    }

    private void updatePersonInfoView(PersonEntity pe){
        Toast.makeText(getContext(), "onChanged PersonInfo", Toast.LENGTH_SHORT).show();
        TextView view;
        // Weight
        view = fragmentView.findViewById(R.id.weightValue_textView);
        view.setText(String.format(Locale.US, "%.1f", pe.getWeight()));
        // Height
        view = fragmentView.findViewById(R.id.heightValue_textView);
        view.setText(String.format(Locale.US, "%.1f", pe.getHeight()));
        // Age
        view = fragmentView.findViewById(R.id.ageValue_textView);
        view.setText(String.format(Locale.US, "%d", pe.getAge()));
        // Gender
        view = fragmentView.findViewById(R.id.genderValue_textView);
        view.setText(pe.getGender());

    }

    private void updateHeight (){

    }

    private void updateAge(){
        TextView ageValue = fragmentView.findViewById(R.id.ageValue_textView);

    }

    private void updateGender(){}
}