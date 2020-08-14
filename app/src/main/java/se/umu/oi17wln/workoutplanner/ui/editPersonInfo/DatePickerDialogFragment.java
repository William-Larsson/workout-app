package se.umu.oi17wln.workoutplanner.ui.editPersonInfo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import se.umu.oi17wln.workoutplanner.R;

/**
 * Dialog fragment used for selecting the users
 * date of birth etc.
 *
 * Author: William Larsson
 */
public class DatePickerDialogFragment
        extends DialogFragment
        implements DatePickerDialog.OnDateSetListener
{
    DatePickerDialog.OnDateSetListener dateSetListener;

    /**
     * Create dialog by selecting current date,
     * unless state is saved elsewhere
     * @param savedInstanceState = state
     * @return = DatePickerDialog.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DatePickerDialog dialog;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dialog = selectConfigurationColorThemeInstance(year, month, day);
        dialog.getDatePicker().setMaxDate(calendar.getTime().getTime());

        return dialog;
    }


    /**
     * Select appropriate theme based on the users
     * system theme of choice. (Light or Dark)
     * @param year = selected year
     * @param month = selected month
     * @param day = selected day
     * @return = Themed DatePickerDialog
     */
    private DatePickerDialog selectConfigurationColorThemeInstance(int year, int month, int day) {
        DatePickerDialog dialog;

        int nightMode = requireContext()
                .getResources()
                .getConfiguration()
                .uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            dialog = new DatePickerDialog(requireActivity(),this, year, month, day);
        } else {
            dialog = new DatePickerDialog(requireActivity(), R.style.DatePickerTheme, this, year, month, day);
        }

        return dialog;
    }


    /**
     * Passes data to external onDateSetListener
     * @param view = DatePicker view
     * @param year = selected year
     * @param month = selected month
     * @param dayOfMonth = selected day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (this.dateSetListener != null) {
            dateSetListener.onDateSet(view, year, month, dayOfMonth);
        }
    }


    /**
     * Sets an external listener for onDateSet
     * @param onDateSetListener = listener Activity/Fragment
     */
    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener){
        this.dateSetListener = onDateSetListener;
    }
}
