package se.umu.oi17wln.workoutplanner.model.exercise;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert
    void insert(ExerciseEntity exerciseEntity);

    @Update
    void update(ExerciseEntity exerciseEntity);

    @Delete
    void delete(ExerciseEntity exerciseEntity);

    @Query("DELETE FROM Tbl_Exercise")
    void deleteAll();

    @Query("SELECT * FROM Tbl_Exercise ORDER BY id DESC")
    LiveData<List<ExerciseEntity>> getAll();
}
