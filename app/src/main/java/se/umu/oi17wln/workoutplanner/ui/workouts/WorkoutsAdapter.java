package se.umu.oi17wln.workoutplanner.ui.workouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;

/**
 *
 */
public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutsHolder> {
    private List<ExerciseEntity> exercises = new ArrayList<>();

    /**
     * Inflate views to the ViewHolder, WorkoutsHOlder
     * @param parent = parent ViewGroup
     * @param viewType = unused
     * @return = the ViewHolder
     */
    @NonNull
    @Override
    public WorkoutsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_cardview, parent, false);

        return new WorkoutsHolder(itemView);
    }

    /**
     * Sets the data from given position in list to the TextViews
     * that the WorkoutHolder holds.
     * @param holder = the WorkoutHolder
     * @param position = position in list
     */
    @Override
    public void onBindViewHolder(@NonNull WorkoutsHolder holder, int position) {
        ExerciseEntity exercise = exercises.get(position);
        holder.nameTextView.setText(exercise.getName());
        holder.setsTextView.setText(
                new StringBuilder().append(exercise.getSets()).append(" sets").toString());
        holder.repsTextView.setText(
                new StringBuilder().append(exercise.getReps()).append(" reps").toString());
    }


    /**
     * Gets number of items in the RecyclerView
     * @return = nr of items
     */
    @Override
    public int getItemCount() {
        return exercises.size();
    }


    public void setExercises(List<ExerciseEntity> exercises){
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    /**
     * Holds the individual view items in the RecyclerView
     */
    class WorkoutsHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView setsTextView;
        private TextView repsTextView;

        public WorkoutsHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseName_textView);
            setsTextView = itemView.findViewById(R.id.exerciseSets_textView);
            repsTextView = itemView.findViewById(R.id.exerciseReps_textView);
        }
    }
}
