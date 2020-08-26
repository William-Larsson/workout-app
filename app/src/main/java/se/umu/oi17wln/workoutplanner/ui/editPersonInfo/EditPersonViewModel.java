package se.umu.oi17wln.workoutplanner.ui.editPersonInfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;
import se.umu.oi17wln.workoutplanner.model.person.PersonRepository;

/**
 * Shared View model for updating and getting info
 * regarding the users weight, age etc.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class EditPersonViewModel extends AndroidViewModel {
    private PersonRepository repo;
    private LiveData<PersonEntity> personInfo;

    /**
     * Constructor, setup repo and LiveData
     * @param app = for context
     */
    public EditPersonViewModel(@NonNull Application app) {
        super(app);
        repo = new PersonRepository(app);
        personInfo = repo.getLatestEntry();
    }

    public void insert(PersonEntity personEntity) {
        repo.insert(personEntity);
    }

    public LiveData<PersonEntity> getLatestPersonInfo() {
        return personInfo;
    }

}
