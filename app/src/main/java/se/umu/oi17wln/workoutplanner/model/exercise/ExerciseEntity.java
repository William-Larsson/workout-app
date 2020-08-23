package se.umu.oi17wln.workoutplanner.model.exercise;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tbl_Exercise")
public class ExerciseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int sets;
    private int reps;


    /**
     * Constructor
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
