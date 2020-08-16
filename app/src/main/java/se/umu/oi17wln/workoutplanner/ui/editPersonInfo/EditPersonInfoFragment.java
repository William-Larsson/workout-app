package se.umu.oi17wln.workoutplanner.ui.editPersonInfo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import se.umu.oi17wln.workoutplanner.MainActivity;
import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.Util;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;

/**
 * A fragment view for updating information about
 * yourself, such as weight, age etc.
 * Updates are written to a shared ViewModel class.
 *
 * Author: William Larsson
 */
public class EditPersonInfoFragment
        extends Fragment
        implements DatePickerDialog.OnDateSetListener
{
    public static final String TAG_WEIGHT = "INPUT_WEIGHT";
    public static final String TAG_HEIGHT = "INPUT_HEIGHT";
    public static final String TAG_DATE = "INPUT_DATE";
    public static final String TAG_IS_MALE = "INPUT_GENDER";

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

        if (savedInstanceState == null) {
            editPersonViewModel.getLatestPersonInfo()
                    .observe(getViewLifecycleOwner(), this::setupPreviousInfo);

        }

        ((MainActivity) requireActivity()).hideBottomNavigationView();
        setHasOptionsMenu(true);
        setupViewInstances();
        setupListeners();

        return fragmentView;
    }


    /**
     * Sets the input fields to match previously given data
     * @param personEntity = the data
     */
    private void setupPreviousInfo(PersonEntity personEntity) {

        if (personEntity != null) {
            weightInput.setText(String.format(Locale.US, Float.toString(personEntity.getWeight())));
            heightInput.setText(String.format(Locale.US, Float.toString(personEntity.getHeight())));
            maleRadioBtn.setChecked(personEntity.isMale());
            String date = personEntity.getDateOfBirth();

            if (date != null && !date.trim().isEmpty()){
                dateOfBirthBtn.setText(new StringBuilder()
                        .append(getResources().getString(R.string.date_of_birth))
                        .append(": ")
                        .append(date).toString()
                );
            }
        }
    }


    /**
     * Save fragment state.
     * @param outState = the saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG_WEIGHT, Objects.requireNonNull(weightInput.getText()).toString());
        outState.putString(TAG_HEIGHT, Objects.requireNonNull(heightInput.getText()).toString());
        outState.putBoolean(TAG_IS_MALE, maleRadioBtn.isChecked());
        outState.putString(TAG_DATE, getDateOfBirthAsString());
    }


    /**
     * Restore state when the underlying activity is
     * rebuilt by the system.
     * @param savedInstanceState = saved onStateInstanceState data
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            weightInput.setText(savedInstanceState.getString(TAG_WEIGHT));
            heightInput.setText(savedInstanceState.getString(TAG_HEIGHT));
            maleRadioBtn.setChecked(savedInstanceState.getBoolean(TAG_IS_MALE));
            String date = savedInstanceState.getString(TAG_DATE);

            if (date != null && !date.trim().isEmpty()){
                dateOfBirthBtn.setText(new StringBuilder()
                        .append(getResources().getString(R.string.date_of_birth))
                        .append(": ")
                        .append(savedInstanceState.getString(TAG_DATE)).toString()
                );
            }
        }
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
    }


    /**
     * Creates the menu for saving
     * @param menu = menu
     * @param inflater = menu inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.editpersoninfo_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Called when an item in the menu has been
     * selected by the user.
     * @param item = item selected
     * @return = true if successful
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_edited_personinfo) {
            saveData();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Saves the user input to the database if all fields are correctly
     * filled.
     */
    private void saveData(){
        String weight = Objects.requireNonNull(weightInput.getText()).toString();
        String height = Objects.requireNonNull(heightInput.getText()).toString();
        boolean isMale = maleRadioBtn.isChecked();
        String dateOfBirth = getDateOfBirthAsString();

        if (weight.trim().isEmpty() || height.trim().isEmpty() || dateOfBirth.trim().isEmpty()) {
            Toast.makeText(
                    requireContext(),
                    "Please fill in all fields above",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            float w = Float.parseFloat(weight);
            float h = Float.parseFloat(height);

            String currentDate = new SimpleDateFormat(Util.DATE_FORMAT, Locale.US)
                    .format(new Date());
            PersonEntity dbEntry = new PersonEntity(h, w, isMale, dateOfBirth, currentDate);
            editPersonViewModel.insert(dbEntry);

            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }


    /**
     * Extracts the date of birth date from the button string.
     * return an empty string if nothing is set
     * @return = the date of birth
     */
    private String getDateOfBirthAsString(){
        String dateOfBirth = dateOfBirthBtn.getText().toString();

        if (dateOfBirth.contains(":")){
            dateOfBirth = dateOfBirth.substring(dateOfBirth.indexOf(":")+1);
        } else {
            dateOfBirth = "";
        }

        return dateOfBirth;
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
        String strYear = Integer.toString(year);
        String strMonth = month < 10 ? "0" + (month+1) : Integer.toString(month);
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
