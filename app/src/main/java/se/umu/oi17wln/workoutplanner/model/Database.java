package se.umu.oi17wln.workoutplanner.model;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityDao;
import se.umu.oi17wln.workoutplanner.model.dailyActivity.DailyActivityEntity;
import se.umu.oi17wln.workoutplanner.model.person.PersonDao;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;

/**
 * Singleton database class that holds all
 * database tables and references to their
 * respective DAO-class for query operations.
 *
 *  Author: William Larsson
 */
@androidx.room.Database(entities = {PersonEntity.class, DailyActivityEntity.class}, version = 1)
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
                    "app_database"
            ).build();
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
    public abstract DailyActivityDao getExerciseDao();
}
