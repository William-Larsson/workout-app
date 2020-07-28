package se.umu.oi17wln.workoutplanner.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A table representation of a persons attributes for the
 * database. Holds values such as height, weight, length etc.
 */
@Entity(tableName = "Tbl_Person")
public class PersonEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private float length;
    private float weight;
    private String dateOfBirth;
    private String gender;
    // time when objects was originally saved
    private String instantDateTime;

    public PersonEntity(
            float length,
            float weight,
            String dateOfBirth,
            String gender,
            String instantDateTime){
        this.length = length;
        this.weight = weight;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.instantDateTime = instantDateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public float getLength() {
        return length;
    }

    public float getWeight() {
        return weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getInstantDateTime(){
        return instantDateTime;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PersonEntity) {
            PersonEntity instance = (PersonEntity) obj;
            if (instance.getLength() == length &&
                    instance.getWeight() == weight &&
                    instance.getDateOfBirth().equals(dateOfBirth) &&
                    instance.getInstantDateTime().equals(instantDateTime)
            ) {
                return true;
            }
        }
        return false;
    }
}
