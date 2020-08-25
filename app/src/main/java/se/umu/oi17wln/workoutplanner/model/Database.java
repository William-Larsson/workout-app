package se.umu.oi17wln.workoutplanner.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityDao;
import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityEntity;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseDao;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;
import se.umu.oi17wln.workoutplanner.model.person.PersonDao;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;

/**
 * Singleton database class that holds all
 * database tables and references to their
 * respective DAO-class for query operations.
 *
 *  Author: William Larsson
 */
@androidx.room.Database(
        entities = {
                PersonEntity.class,
                DailyActivityEntity.class,
                ExerciseEntity.class
        },
        version = 1
)
public abstract class Database extends RoomDatabase {

    /*
     * Singleton instance is written directly to main
     * memory to avoid thread memory cache errors.
     */
    private static volatile Database dbInstance;


    /**
     * Instantiate and get singleton database instance.
     * @param context = app context
     * @return = the db instance
     */
    public static synchronized Database getInstance(Context context){
        if (dbInstance == null){
            dbInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class,
                    "app_database")
                    .addCallback(roomCallback) // TODO: remove when below callback gets removed
                    .build();
        }
        return dbInstance;
    }


    /**
     * Used to get access the DAO operations
     * @return = PersonDAO
     */
    public abstract PersonDao getPersonDao();


    /**
     * Used to get access to DAO operations
     * @return = DailyActivityDao
     */
    public abstract DailyActivityDao getDailyActivityDao();



    /**
     * Used to get access to DAO operations
     * @return = ExerciseDao
     */
    public abstract ExerciseDao getExerciseDao();


    /**
     * Callback for Room to instantiate the Exercise table with some
     * default values.
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        /**
         * Populate the db table on a background thread.
         * @param db = database to insert to
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(new PopulateExerciseTableRunnable(dbInstance)).start();
        }
    };

    /**
     * Runnable for populating the database table
     */
    private static class PopulateExerciseTableRunnable implements Runnable {
        private ExerciseDao dao;

        private PopulateExerciseTableRunnable(Database db){
            dao = db.getExerciseDao();
        }

        /**
         * Insert these values to Exercise table using ExerciseDao
         */
        @Override
        public void run() {
            dao.insert(new ExerciseEntity("Example Exercise 1", 3, 8));
            dao.insert(new ExerciseEntity("Example Exercise 2", 4, 12));
        }
    }
}
