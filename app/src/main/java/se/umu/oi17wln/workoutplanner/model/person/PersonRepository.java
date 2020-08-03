package se.umu.oi17wln.workoutplanner.model.person;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import se.umu.oi17wln.workoutplanner.model.Database;

/**
 * Class that abstracts data operations to and from the ViewModel
 * and the Room database as an API. Acts as a single source of truth
 * for all the data regarding PersonEntity.That way the ViewModel
 * doesn't need to know where the data comes from, or where it's
 * being fetched in case that ever needs to change.
 *
 * OBS! Must contain methods for all database operations in DAO.
 *
 * Author: William Larsson
 */
public class PersonRepository {
    private PersonDao dao;
    private LiveData<List<PersonEntity>> allPersonEntries;
    private LiveData<PersonEntity> latestPersonEntry;

    /**
     * Setup database connection, DAO-operations and
     * link the LiveData.
     *
     * @param app = used for Context.
     */
    public PersonRepository(Application app){
        Database db = Database.getInstance(app);
        dao = db.getPersonDao();
        allPersonEntries = dao.getAll();
        latestPersonEntry = dao.getLatestEntry();
    }


    /**
     * Starts a new background thread which will add a PersonEntity
     * to the database.
     * @param person = data to insert.
     */
    public void insert(PersonEntity person){
        new Thread(new InsertRunnable(dao, person)).start();
    }


    /**
     * Starts a new background thread which will update the data
     * of an existing PersonEntity entry in the database.
     * @param person = entry to update, with its new data.
     */
    public void update(PersonEntity person) {
        new Thread(new UpdateRunnable(dao, person)).start();
    }


    /**
     * Deletes a given PersonEntity entry from the database
     * asynchronously on a background thread.
     * @param person = entry to delete.
     */
    public void delete(PersonEntity person) {
        new Thread(new DeleteRunnable(dao, person)).start();
    }


    /**
     * Deletes all PersonEntity entries from the database
     * asynchronously on a background thread.
     */
    public void deleteAll() {
        new Thread(new DeleteAllRunnable(dao)).start();
    }


    /**
     * LiveData getter from the full list of Person-entries
     * @return = list of PersonEntity objects
     */
    public LiveData<List<PersonEntity>> getAllPersonEntries() {
        return allPersonEntries;
    }


    /**
     * LiveData getter for the latest Person-entry.
     * @return = latest PersonEntity object.
     */
    public LiveData<PersonEntity> getLatestPersonEntry() {
        return latestPersonEntry;
    }


    // --------------- Nested classes for thread operations --------------- //


    /**
     * Runnable for inserting to the database in the background.
     * Static is used to separate nested class from parent, to
     * avoid potential memory leaks.
     */
    private static class InsertRunnable implements Runnable {
        private PersonDao dao;
        private PersonEntity data;

        public InsertRunnable(PersonDao dao, PersonEntity dataToInsert){
            this.dao = dao;
            this.data = dataToInsert;
        }

        @Override
        public void run() {
            dao.insert(data);
        }
    }


    /**
     * Runnable for updating a database entry in the background.
     * Static is used to separate nested class from parent, to
     * avoid potential memory leaks.
     */
    private static class UpdateRunnable implements Runnable {
        private PersonDao dao;
        private PersonEntity data;

        public UpdateRunnable(PersonDao dao, PersonEntity dataToUpdate){
            this.dao = dao;
            this.data = dataToUpdate;
        }

        @Override
        public void run() {
            dao.update(data);
        }
    }


    /**
     * Runnable for deleting a database entry in the background.
     * Static is used to separate nested class from parent, to
     * avoid potential memory leaks.
     */
    private static class DeleteRunnable implements Runnable {
        private PersonDao dao;
        private PersonEntity data;

        public DeleteRunnable(PersonDao dao, PersonEntity dataToDelete) {
            this.dao = dao;
            this.data = dataToDelete;
        }

        @Override
        public void run() {
            dao.delete(data);
        }
    }


    /**
     * Runnable for deleting a database entry in the background.
     * Static is used to separate nested class from parent, to
     * avoid potential memory leaks.
     */
    private static class DeleteAllRunnable implements Runnable {
        private PersonDao dao;

        public DeleteAllRunnable(PersonDao dao) {
            this.dao = dao;
        }

        @Override
        public void run() {
            dao.deleteAll();
        }
    }
}
