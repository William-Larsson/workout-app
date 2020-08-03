package se.umu.oi17wln.workoutplanner.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import se.umu.oi17wln.workoutplanner.model.person.PersonDao;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;
import se.umu.oi17wln.workoutplanner.model.person.PersonRepository;

/**
 * ViewModel for the HomeFragment / landing page.
 * Acts as a middle man between a View and the underlying
 * business logic / model classes.
 * Provides data to a View by accessing a repository and
 * providing the returned data as LiveData.
 *
 * Author: William Larsson
 */
public class HomeViewModel extends AndroidViewModel {

    private PersonRepository repo;
    private LiveData<PersonEntity> personInfo;

    public HomeViewModel(@NonNull Application app) {
        super(app);
        repo = new PersonRepository(app);
        personInfo = repo.getLatestPersonEntry();
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


    public LiveData<PersonEntity> getPersonInfo() {
        return personInfo;
    }
}