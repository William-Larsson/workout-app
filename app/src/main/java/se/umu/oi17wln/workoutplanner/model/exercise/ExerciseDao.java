package se.umu.oi17wln.workoutplanner.model.exercise;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;


/**
 * "Data access object"-class used to access the Room database data
 * for the Exercise-table. Class in an interface to allow for
 * abstract access to the application database.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
@Dao
public interface ExerciseDao {
    /**
     * Insert to database
     * @param exerciseEntity = object to insert
     */
    @Insert
    void insert(ExerciseEntity exerciseEntity);


    /**
     * Update a given db entry
     * @param exerciseEntity = entry to update
     */
    @Update
    void update(ExerciseEntity exerciseEntity);


    /**
     * Delete given entry from db
     * @param exerciseEntity = entry to delete
     */
    @Delete
    void delete(ExerciseEntity exerciseEntity);


    /**
     * Delete all rows from db
     */
    @Query("DELETE FROM Tbl_Exercise")
    void deleteAll();


    /**
     * Get all rows from db in descending order (newest first)
     * @return = LiveData list of ExerciseEntity
     */
    @Query("SELECT * FROM Tbl_Exercise ORDER BY id DESC")
    LiveData<List<ExerciseEntity>> getAll();
}
