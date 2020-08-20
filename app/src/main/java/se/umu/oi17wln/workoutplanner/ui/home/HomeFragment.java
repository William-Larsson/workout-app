package se.umu.oi17wln.workoutplanner.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.Locale;
import se.umu.oi17wln.workoutplanner.MainActivity;
import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;
import se.umu.oi17wln.workoutplanner.ui.editPersonInfo.EditPersonInfoFragment;
import se.umu.oi17wln.workoutplanner.ui.editPersonInfo.EditPersonViewModel;

/**
 * View class for handling immediate actions related
 * to the GUI-component of this fragment. Updates components
 * from ViewModel data and executes Android specific
 * operations related to the GUI.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class HomeFragment extends Fragment implements SensorEventListener {

    private View fragmentView;
    private EditPersonViewModel editPersonViewModel;
    // attributes for step counter
    private static final int STEP_COUNTER_PERMISSION_CODE = 1;
    private SensorManager sensorManager;
    private boolean running;
    private float totalSteps = 0;
    private float previousStepsSinceBoot = 0;

    public View onCreateView (
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState )
    {
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        editPersonViewModel = new ViewModelProvider(requireActivity()).get(EditPersonViewModel.class);
        editPersonViewModel.getLatestPersonInfo()
                .observe(getViewLifecycleOwner(), this::updatePersonInfoView);

        // for step counter
        checkStepCounterPermission();
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);


        setupHeadlineTexts();
        setupViewListeners();

        fragmentView.setVisibility(View.VISIBLE);

        return fragmentView;
    }


    /**
     * Setup all listeners used in the fragment
     */
    private void setupViewListeners() {
        ImageButton editMenu = fragmentView.findViewById(R.id.edit_personInfo_menu);
        editMenu.setOnClickListener((view) -> showPopupMenu(editMenu));
    }


    /**
     * Show the popup menu and it's items.
     * @param view = view where interaction happened.
     */
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.personinfo_cardview_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this::startEditPersonInfoFragment);
        popup.show();
    }


    /**
     *
     * @param item
     * @return
     */
    private boolean startEditPersonInfoFragment(MenuItem item) {
        if (item.getItemId() == R.id.edit_personInfo) {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            ((ViewGroup) requireView().getParent()).getId(),
                            new EditPersonInfoFragment(),
                            MainActivity.TAG_EDIT_PERSON_INFO
                    )
                    .addToBackStack("EditPersonInfoBackStack")
                    .commit();
            return true;
        }
        return false;
    }


    /**
     * Setup the headline text instead of the
     * generic standard text
     */
    private void setupHeadlineTexts() {
        TextView view = fragmentView.findViewById(R.id.headline);
        view.setText("About you");
    }


    /**
     * Update the view displaying personal info
     * when there is new data.
     * @param pe = PersonEntity with updated data.
     */
    private void updatePersonInfoView(PersonEntity pe){

        if (pe != null) {
            TextView view;

            // Weight
            view = fragmentView.findViewById(R.id.weightValue_textView);
            String weight = String.format(Locale.US, "%.1f", pe.getWeight());
            if (weight.endsWith(".0")) {
                weight = weight.replace(".0", "");
            }
            view.setText(weight);

            // Height
            view = fragmentView.findViewById(R.id.heightValue_textView);
            String height = String.format(Locale.US, "%.2f", pe.getHeight());
            if (height.endsWith(".00")) {
                height = height.replace(".00", "");
            }
            view.setText(height);

            // Age
            view = fragmentView.findViewById(R.id.ageValue_textView);
            view.setText(String.format(Locale.US, "%d", pe.getAge()));

            // Gender
            view = fragmentView.findViewById(R.id.genderValue_textView);
            view.setText(pe.isMale() ? "Male" : "Female");
        }
    }


    // -------------------- For the step counter -------------------- //


    /**
     * Check user has accepted permission to use the
     * step sensor by accepting ACTIVITY_RECOGNITION
     */
    private void checkStepCounterPermission(){
            if (ContextCompat
                    .checkSelfPermission(requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION)
                    == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(requireActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
            } else {
                requestStepCounterPermission();
            }
    }


    /**
     * Request user permission for all devices running API 29
     * or higher.
     */
    private void requestStepCounterPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION)
        ) {
            showPermissionExplanationDialog();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(
                        requireActivity(),
                        new String[] {Manifest.permission.ACTIVITY_RECOGNITION},
                        STEP_COUNTER_PERMISSION_CODE);
            }
        }
    }


    /**
     * Show dialog explaining why this app need permission if the
     * user has previously denied, but not check "Don't ask again"
     */
    private void showPermissionExplanationDialog(){
        new AlertDialog.Builder(requireContext())
                .setTitle("User permission needed")
                .setMessage("This app needs your permission in order to count the number of " +
                        "steps you take during the day. Some application functionality " +
                        "will be limited without this permission.")
                .setPositiveButton("ok", (dialog, which) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ActivityCompat.requestPermissions(
                                requireActivity(),
                                new String[] {Manifest.permission.ACTIVITY_RECOGNITION},
                                STEP_COUNTER_PERMISSION_CODE);
                    }
                })
                .setNegativeButton("cancel", ((dialog, which) -> {
                    dialog.dismiss();
                }))
                .create()
                .show();
    }


    /**
     * Listens for Permission changes. If the change is regarding
     * the step counter, make a toast displaying if permission
     * is granted or denied.
     * @param requestCode = only checks STEP_COUNTER_PERMISSION_CODE
     * @param permissions = ?
     * @param grantResults = contains permission statuses
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == STEP_COUNTER_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        requireContext(),
                        "Step Counter permission granted",
                        Toast.LENGTH_SHORT
                ).show();
            } else  {
                Toast.makeText(
                        requireContext(),
                        "Permission denied",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }


    /**
     * When app is resumed, registers step counter listener
     * if sensor exists on this device.
     */
    @Override
    public void onResume() {
        super.onResume();
        running = true;

        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(requireContext(), "No step sensor found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Step sensor found", Toast.LENGTH_SHORT).show();
            // TODO: Check difference between SENSOR_DELAY_UI and its alternatives.
            sensorManager.registerListener(HomeFragment.this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }


    /**
     * Sensor has been used, update step data
     * @param event = value from changed sensor state
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0]; // get current total steps from sensor
            int currentSteps = (int) totalSteps - (int) previousStepsSinceBoot;
            TextView textView = fragmentView.findViewById(R.id.temp_steps);
            textView.setText(String.format(Locale.US, "%d", currentSteps));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // unused
    }
}