package se.umu.oi17wln.workoutplanner.ui.home;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.Locale;
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
 */
public class HomeFragment extends Fragment {

    private View fragmentView;
    private EditPersonViewModel editPersonViewModel;

    public View onCreateView (
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState )
    {
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        //TODO: Assert that requireActivity() is the correct. could be "this" or something else
        editPersonViewModel = new ViewModelProvider(requireActivity()).get(EditPersonViewModel.class);
        editPersonViewModel.getLatestPersonInfo()
                .observe(getViewLifecycleOwner(), this::updatePersonInfoView);

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
        Toast.makeText(getContext(), "Menu pressed", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Edit pressed", Toast.LENGTH_SHORT).show();

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(((ViewGroup) requireView().getParent()).getId(),new EditPersonInfoFragment())
                    .addToBackStack("tag")
                    .commit();

            //fragmentView.setVisibility(View.GONE);

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
        view.setText("Hello");
    }


    /**
     * Update the view displaying personal info
     * when there is new data.
     * @param pe = PersonEntity with updated data.
     */
    private void updatePersonInfoView(PersonEntity pe){
        //TODO: REMOVE Toast.makeText(getContext(), "onChanged PersonInfo", Toast.LENGTH_SHORT).show();

        if (pe != null) {
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
            view.setText(pe.isMale() ? "Male" : "Female");
        }
    }
}