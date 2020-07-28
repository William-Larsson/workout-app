package se.umu.oi17wln.workoutplanner.model;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {PersonEntity.class}, version = 1)
public abstract class Database extends RoomDatabase {

    /*
     * Singleton instance is written directly to main
     * memory for thread visibility to avoid errors.
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
     * Used to access the DAO operations
     * @return = PersonDAO
     */
    public abstract PersonDao getPersonDao();

}
