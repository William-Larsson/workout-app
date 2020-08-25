package se.umu.oi17wln.workoutplanner.model.exercise;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import se.umu.oi17wln.workoutplanner.model.Database;


/**
 * Class that abstracts data operations to and from the ViewModel
 * and the Room database as an API. Acts as a single source of truth
 * for all the data regarding ExerciseEntity.That way the ViewModel
 * doesn't need to know where the data comes from, or where it's
 * being fetched in case that ever needs to change.
 *
 * OBS! Must contain methods for all database operations in DAO.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class ExerciseRepository {
    private ExerciseDao dao;
    private LiveData<List<ExerciseEntity>> allExercises;

    /**
     * Setup database connection, DAO-operations and
     * link the LiveData.
     * Defaults to the current date
     *
     * @param app = used for Context.
     */
    public ExerciseRepository(Application app) {
        Database db = Database.getInstance(app);
        this.dao = db.getExerciseDao();
        this.allExercises = dao.getAll();
    }

    /**
     * Insert entry to db on a new thread
     * @param exerciseEntity = entry to insert
     */
    public void insert(ExerciseEntity exerciseEntity){
        new Thread(new InsertRunnable(dao, exerciseEntity)).start();
    }


    /**
     * Update an existing entry to db on a new thread
     * @param exerciseEntity = entry to update (id must match)
     */
    public void update(ExerciseEntity exerciseEntity){
        new Thread(new UpdateRunnable(dao, exerciseEntity)).start();
    }


    /**
     * Delete given entry in db on a new thread
     * @param exerciseEntity = entry to delete
     */
    public void delete(ExerciseEntity exerciseEntity){
        new Thread(new DeleteRunnable(dao, exerciseEntity)).start();
    }


    /**
     * Delete all DailyActivity db entries
     */
    public void deleteAll(){
        new Thread(new DeleteAllRunnable(dao)).start();
    }


    /**
     * Get all Exercise db entries as LiveData
     * @return = LiveData with list of all entries
     */
    public LiveData<List<ExerciseEntity>> getAllExercises(){
        return allExercises;
    }


    // --------------- Nested classes for thread operations --------------- //


    private static class InsertRunnable implements Runnable {
        private ExerciseDao dao;
        private ExerciseEntity data;

        public InsertRunnable(ExerciseDao dao, ExerciseEntity data) {
            this.dao = dao;
            this.data = data;
        }

        @Override
        public void run() {
            dao.insert(data);
        }
    }


    private static class UpdateRunnable implements Runnable {
        private ExerciseDao dao;
        private ExerciseEntity data;

        public UpdateRunnable(ExerciseDao dao, ExerciseEntity data) {
            this.dao = dao;
            this.data = data;
        }

        @Override
        public void run() {
            dao.update(data);
        }
    }


    private static class DeleteRunnable implements Runnable {
        private ExerciseDao dao;
        private ExerciseEntity data;

        public DeleteRunnable(ExerciseDao dao, ExerciseEntity data) {
            this.dao = dao;
            this.data = data;
        }

        @Override
        public void run() {
            dao.delete(data);
        }
    }


    private static class DeleteAllRunnable implements Runnable {
        private ExerciseDao dao;

        public DeleteAllRunnable(ExerciseDao dao) {
            this.dao = dao;
        }

        @Override
        public void run() {
            dao.deleteAll();
        }
    }
}
