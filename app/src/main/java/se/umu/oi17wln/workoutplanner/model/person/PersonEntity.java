package se.umu.oi17wln.workoutplanner.model.person;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A table representation of a persons attributes for the
 * database. Table is automatically generated because of
 * "@Entity"-tag. Holds values such as height, weight, length etc.
 *
 * Author: William Larsson
 */
@Entity(tableName = "Tbl_Person")
public class PersonEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private float height;
    private float weight;
    private boolean isMale;
    private String dateOfBirth;
    // time when objects was originally saved
    private String dateOfCreation;

    /**
     * Constructor
     * @param height = person height
     * @param weight = person weight
     * @param isMale = person gender
     * @param dateOfBirth = person date of birth
     * @param dateOfCreation = date the sbentry was made
     */
    public PersonEntity(
            float height,
            float weight,
            boolean isMale,
            String dateOfBirth,
            String dateOfCreation){
        this.height = height;
        this.weight = weight;
        this.dateOfBirth = dateOfBirth;
        this.isMale = isMale;
        this.dateOfCreation = dateOfCreation;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isMale() {
        return isMale;
    }

    public String getDateOfCreation(){
        return dateOfCreation;
    }


    /**
     * Get the age as a number instead of as a date formatted String.
     * @return = age a String.
     */
    public int getAge(){
        int age;
        Calendar birthDay = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        Date birthDayDate = null;

        try {
            // TODO: Change the pattern?
            birthDayDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(dateOfBirth);
        } catch (ParseException e) {
            Log.e("ParseError",
                    "Could not parse dateOfBirth string variable. Check formatting");
            return 0;
        }

        if (birthDayDate != null) birthDay.setTime(birthDayDate);
        else return 0;

        age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < birthDay.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }


    /**
     * Equals method for testing.
     * @param obj = Person Entity to test against
     * @return = true is same values, else false
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PersonEntity) {
            PersonEntity instance = (PersonEntity) obj;
            if (instance.getHeight() == height &&
                    instance.getWeight() == weight &&
                    instance.isMale == isMale &&
                    instance.getDateOfBirth().equals(dateOfBirth) &&
                    instance.getDateOfCreation().equals(dateOfCreation)
            ) {
                return true;
            }
        }
        return false;
    }
}
