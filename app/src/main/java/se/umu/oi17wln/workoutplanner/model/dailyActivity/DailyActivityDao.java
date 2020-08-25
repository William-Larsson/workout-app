package se.umu.oi17wln.workoutplanner.model.dailyActivity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * "Data access object"-class used to access the Room database data
 * for the DailyActivity-table. Class in an interface to allow for
 * abstract access to the application database.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
@Dao
public interface DailyActivityDao {

    @Insert
    void insert(DailyActivityEntity dailyActivityEntity);

    @Update
    void update (DailyActivityEntity dailyActivityEntity);

    @Delete
    void delete(DailyActivityEntity dailyActivityEntity);


    /**
     * Delete all entries in the table.
     */
    @Query("DELETE FROM Tbl_DailyActivity")
    void deleteAll();


    /**
     * Get all entries, sorted from newest to oldest.
     * @return = all database entries.
     */
    @Query("SELECT * FROM Tbl_DailyActivity ORDER BY id DESC")
    LiveData<List<DailyActivityEntity>> getAll();


    /**
     * Get only the latest entry from the database.
     * @return = the latest entry.
     */
    @Query("SELECT * FROM Tbl_DailyActivity ORDER BY id DESC LIMIT 1")
    LiveData<DailyActivityEntity> getLatestEntry();


    /**
     * Get a db entry by the given date.
     * @param date = a date following Util.DATE_FORMAT pattern
     * @return = Entity from given date, if exists.
     */
    @Query("SELECT * FROM Tbl_DailyActivity WHERE date = :date")
    LiveData<DailyActivityEntity> getEntryByDate(String date);

}
