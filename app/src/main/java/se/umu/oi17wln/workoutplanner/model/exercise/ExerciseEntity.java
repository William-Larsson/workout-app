package se.umu.oi17wln.workoutplanner.model.exercise;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A table representation of an exercise and its execution.
 * Table is automatically generated because of
 * "@Entity"-tag. Holds values for exercise name, sets and reps.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
@Entity(tableName = "Tbl_Exercise")
public class ExerciseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int sets;
    private int reps;


    /**
     * Constructor. Does not contain ID because that will
     * be set by the automatic primary key
     * @param name = exercise name
     * @param sets = nr of sets
     * @param reps = nr of reps
     */
    public ExerciseEntity(String name, int sets, int reps) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

}
