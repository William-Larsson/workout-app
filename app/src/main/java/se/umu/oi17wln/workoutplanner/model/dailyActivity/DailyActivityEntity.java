package se.umu.oi17wln.workoutplanner.model.dailyActivity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A table representation of exercise measurement during a day.
 * Table is automatically generated because of
 * "@Entity"-tag. Holds values such as total steps, date of creation etc.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
@Entity(tableName = "Tbl_DailyActivity")
public class DailyActivityEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int dailyTotalSteps;
    private int totalStepsSinceBoot;
    private String date;
    // TODO: Add daily km, kcal, moved minutes later?

    public DailyActivityEntity(
            int dailyTotalSteps,
            int totalStepsSinceBoot,
            String date
    ) {
        this.dailyTotalSteps = dailyTotalSteps;
        this.totalStepsSinceBoot = totalStepsSinceBoot;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getDailyTotalSteps() {
        return dailyTotalSteps;
    }

    public int getTotalStepsSinceBoot() {
        return totalStepsSinceBoot;
    }

    public String getDate() {
        return date;
    }
}
