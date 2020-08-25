package se.umu.oi17wln.workoutplanner.model.person;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * "Data access object"-class used to access the Room database data
 * for the Person-table. Class in an interface to allow for
 * abstract access to the application database.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
@Dao
public interface PersonDao {

    /**
     * Insert a new entry to the database
     * @param personEntity = data to insert
     */
    @Insert
    void insert(PersonEntity personEntity);


    /**
     * Update an existing entry
     * @param personEntity = updated data
     */
    @Update
    void update(PersonEntity personEntity);


    /**
     * Delete a given entry.
     * @param personEntity = entry to delete
     */
    @Delete
    void delete(PersonEntity personEntity);


    /**
     * Delete all entries in the table.
     */
    @Query("DELETE FROM Tbl_Person")
    void deleteAll();


    /**
     * Get all entries, sorted from newest to oldest.
     * @return = all database entries.
     */
    @Query("SELECT * FROM Tbl_Person ORDER BY id DESC")
    LiveData<List<PersonEntity>> getAll();


    /**
     * Get only the latest entry from the database.
     * @return = the latest entry.
     */
    @Query("SELECT * FROM Tbl_Person ORDER BY id DESC LIMIT 1")
    LiveData<PersonEntity> getLatestEntry();

}
