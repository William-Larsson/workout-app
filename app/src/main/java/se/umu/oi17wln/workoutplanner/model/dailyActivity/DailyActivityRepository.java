package se.umu.oi17wln.workoutplanner.model.dailyActivity;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import se.umu.oi17wln.workoutplanner.model.Database;

/**
 * Class that abstracts data operations to and from the ViewModel
 * and the Room database as an API. Acts as a single source of truth
 * for all the data regarding DailyActivityEntity.That way the ViewModel
 * doesn't need to know where the data comes from, or where it's
 * being fetched in case that ever needs to change.
 *
 * OBS! Must contain methods for all database operations in DAO.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class DailyActivityRepository {
    private DailyActivityDao dao;
    private LiveData<List<DailyActivityEntity>> allDailyEntries;
    private LiveData<DailyActivityEntity> latestDailyEntry;

    /**
     * Setup database connection, DAO-operations and
     * link the LiveData.
     * Defaults to the current date
     *
     * @param app = used for Context.
     */
    public DailyActivityRepository(Application app) {
        Database db = Database.getInstance(app);
        dao = db.getDailyActivityDao();
        // LiveData
        allDailyEntries = dao.getAll();
        latestDailyEntry = dao.getLatestEntry();
        // dailyEntryByDate = dao.getEntryByDate(Util.getCurrentDate());
    }

    public void insert(DailyActivityEntity dae) {
        new Thread(new InsertRunnable(dao, dae)).start();
    }

    public void update(DailyActivityEntity dae) {
        new Thread(new UpdateRunnable(dao, dae)).start();
    }

    public void delete(DailyActivityEntity dae) {
        new Thread(new DeleteRunnable(dao, dae)).start();
    }

    public void deleteAll() {
        new Thread(new DeleteAllRunnable(dao)).start();
    }

    public LiveData<List<DailyActivityEntity>> getAllEntries() {
        return allDailyEntries;
    }

    public LiveData<DailyActivityEntity> getLatestEntry(){
        return latestDailyEntry;
    }

    // TODO: check if this works ?
    public LiveData<DailyActivityEntity> getEntryByDate(String date) {
        return dao.getEntryByDate(date);
    }


    // --------------- Nested classes for thread operations --------------- //


    private static class InsertRunnable implements Runnable {
        private DailyActivityDao dao;
        private DailyActivityEntity data;

        public InsertRunnable(DailyActivityDao dao, DailyActivityEntity data) {
            this.dao = dao;
            this.data = data;
        }

        @Override
        public void run() {
            dao.insert(data);
        }
    }

    private static class UpdateRunnable implements Runnable {
        private DailyActivityDao dao;
        private DailyActivityEntity data;

        public UpdateRunnable(DailyActivityDao dao, DailyActivityEntity data) {
            this.dao = dao;
            this.data = data;
        }

        @Override
        public void run() {
            dao.update(data);
        }
    }

    private static class DeleteRunnable implements Runnable {
        private DailyActivityDao dao;
        private DailyActivityEntity data;

        public DeleteRunnable(DailyActivityDao dao, DailyActivityEntity data) {
            this.dao = dao;
            this.data = data;
        }

        @Override
        public void run() {
            dao.delete(data);
        }
    }

    private static class DeleteAllRunnable implements Runnable {
        private DailyActivityDao dao;

        public DeleteAllRunnable(DailyActivityDao dao) {
            this.dao = dao;
        }

        @Override
        public void run() {
            dao.deleteAll();
        }
    }
}
