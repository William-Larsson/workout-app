package se.umu.oi17wln.workoutplanner.ui.editPersonInfo;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import se.umu.oi17wln.workoutplanner.MainActivity;
import se.umu.oi17wln.workoutplanner.R;

/**
 * A fragment view for updating information about
 * yourself, such as weight, age etc.
 * Updates are written to a shared ViewModel class.
 *
 * Author: William Larsson
 */
public class EditPersonInfoFragment
        extends Fragment
        implements DatePickerDialog.OnDateSetListener {
    private View fragmentView;
    private EditPersonViewModel editPersonViewModel;
    private TextInputEditText weightInput;
    private TextInputEditText heightInput;
    private Button dateOfBirthBtn;
    private RadioGroup genderInputGroup;
    private RadioButton maleRadioBtn;
    private RadioButton femaleRadioBtn;
    private CardView cardView;

    /**
     * Create the fragments view and setup methods for
     * interacting with the UI-components.
     *
     * @param inflater           = view inflater
     * @param container          = view container
     * @param savedInstanceState = saved state
     * @return = the fragments view
     */
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_editpersoninfo, container, false);
        editPersonViewModel = new ViewModelProvider(
                requireActivity()).get(EditPersonViewModel.class);

        ((MainActivity) requireActivity()).hideBottomNavigationView();

        setupViewInstances();
        setupListeners();

        return fragmentView;
    }


    /**
     * Set up references to all relevant UI components
     */
    private void setupViewInstances() {
        weightInput = fragmentView.findViewById(R.id.user_input_weight);
        heightInput = fragmentView.findViewById(R.id.user_input_height);
        dateOfBirthBtn = fragmentView.findViewById(R.id.btn_birthDate);
        genderInputGroup = fragmentView.findViewById(R.id.gender_radio_group);
        maleRadioBtn = fragmentView.findViewById(R.id.radio_btn_male);
        femaleRadioBtn = fragmentView.findViewById(R.id.radio_btn_female);
    }


    /**
     * Set up listeners for views in the fragment.
     */
    private void setupListeners() {
        dateOfBirthBtn.setOnClickListener(this::showDatePicker);
        genderInputGroup.setOnCheckedChangeListener(this::swapGenderButtonTextColor);
    }


    /**
     * Swap the text color of the buttons
     * @param radioGroup = not used
     * @param i = not used
     */
    private void swapGenderButtonTextColor(RadioGroup radioGroup, int i) {
        if (maleRadioBtn.isChecked()){
            maleRadioBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite));
            femaleRadioBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBackgroundDark));
        } else {
            femaleRadioBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite));
            maleRadioBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBackgroundDark));
        }
    }


    /**
     * Show the DatePickerDialog and set this to listener
     * @param view = view
     */
    private void showDatePicker(View view) {
            DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
            datePicker.show(requireActivity().getSupportFragmentManager(), "Date picker");
            datePicker.setOnDateSetListener(this);
    }


    /**
     * Listener method for changing state when the user
     * selects a date from the date picker dialog.
     * @param view = view
     * @param year = the year selected
     * @param month = the month selected
     * @param dayOfMonth = the day selected
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        */
        String strYear = Integer.toString(year);
        String strMonth = month < 10 ? "0" + month : Integer.toString(month);
        String strDay = dayOfMonth < 10 ? "0" + dayOfMonth : Integer.toString(dayOfMonth);

        dateOfBirthBtn.setText(new StringBuilder()
                .append(getResources().getString(R.string.date_of_birth))
                .append(": ").append(strYear)
                .append("-").append(strMonth)
                .append("-").append(strDay)
                .toString()
        );
    }


    /**
     * View is soon to be destroyed. Show navigation again.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (((MainActivity) requireActivity()).getBottomNavigationViewVisibility() == View.GONE){
            ((MainActivity) requireActivity()).showBottomNavigationView();
        }
    }
}
