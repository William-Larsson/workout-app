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

    public EditPersonViewModel(@NonNull Application app) {
        super(app);
        repo = new PersonRepository(app);
        personInfo = repo.getLatestEntry();
    }

    // TODO: change the following method names to something more appropriate?
    public void insert(PersonEntity personEntity) {
        repo.insert(personEntity);
    }


    public void update(PersonEntity personEntity){
        repo.update(personEntity);
    }


    public void delete(PersonEntity personEntity) {
        repo.delete(personEntity);
    }


    public void deleteAll(){
        repo.deleteAll();
    }


    public LiveData<PersonEntity> getLatestPersonInfo() {
        return personInfo;
    }
}
