package se.umu.oi17wln.workoutplanner.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel for the ProfileFragment.
 * Acts as a middle man between a View and the underlying
 * business logic / model classes.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Work in progress");
    }

    public LiveData<String> getText() {
        return mText;
    }
}