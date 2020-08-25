package se.umu.oi17wln.workoutplanner.ui.workouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import se.umu.oi17wln.workoutplanner.R;
import se.umu.oi17wln.workoutplanner.model.exercise.ExerciseEntity;

/**
 * Custom ListAdapter class for use in a RecyclerView.
 * Implemented to automatically update with LiveData and
 * built-in list animations.
 *
 * Author: William Larsson
 * Course: Development of mobile applications, 5DV209
 */
public class WorkoutsAdapter extends ListAdapter<ExerciseEntity, WorkoutsAdapter.WorkoutsHolder> {
    private OnMenuButtonClickListener listener;

    /**
     * Constructor used for comparing two lists using DiffUtil,
     * This will enable animations to be displayed on insert/delete/edit
     */
    public WorkoutsAdapter() {
        super(DIFF_CALLBACK);
    }


    /**
     * Callback implementation for comparing contents in
     * two lists, old and new
     */
    private static final DiffUtil.ItemCallback<ExerciseEntity> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<ExerciseEntity>()
    {
        /**
         * Check if items in the list are the same.
         * This is done by comparing there unique id,
         * which is their primary key in the db
         * @param oldItem = item from old list
         * @param newItem = item from new list
         * @return = true is same
         */
        @Override
        public boolean areItemsTheSame(
                @NonNull ExerciseEntity oldItem,
                @NonNull ExerciseEntity newItem)
        {
            return oldItem.getId() == newItem.getId();
        }

        /**
         * True if no data values within the "same" item has changed
         * @param oldItem = item from old list
         * @param newItem = item from new list
         * @return = true if no change
         */
        @Override
        public boolean areContentsTheSame(
                @NonNull ExerciseEntity oldItem,
                @NonNull ExerciseEntity newItem)
        {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getSets() == newItem.getSets()
                    && oldItem.getReps() == newItem.getReps();
        }
    };


    /**
     * Inflate views to the ViewHolder, WorkoutsHolder
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
        ExerciseEntity exercise = getItem(position);
        holder.nameTextView.setText(exercise.getName());
        holder.setsTextView.setText(
                new StringBuilder().append(exercise.getSets()).append(" sets").toString());
        holder.repsTextView.setText(
                new StringBuilder().append(exercise.getReps()).append(" reps").toString());
    }


    /**
     * Returns item at given list position
     * @param position = position in the list
     * @return = the corresponding ExerciseEntity object
     */
    public ExerciseEntity getExerciseAt(int position){
        return getItem(position);
    }


    /**
     * Holds the individual view items in the RecyclerView
     */
    class WorkoutsHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView setsTextView;
        private TextView repsTextView;
        private ImageButton optionsBtn;

        public WorkoutsHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseName_textView);
            setsTextView = itemView.findViewById(R.id.exerciseSets_textView);
            repsTextView = itemView.findViewById(R.id.exerciseReps_textView);
            optionsBtn = itemView.findViewById(R.id.exercise_options_menu);

            optionsBtn.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.OnMenuClick(optionsBtn, getItem(position));
                }
            });
        }
    }


    /**
     * Functional interface for adding a custom onClick event.
     * Provides the view that triggered the click as well as the
     * ExerciseEntity that that is displayed in the parent view.
     */
    public interface OnMenuButtonClickListener{
        void OnMenuClick(View exerciseView, ExerciseEntity exerciseEntity);
    }


    /**
     * Sets the given listener to the method that should run when
     * a options menu button is clicked.
     * @param listener = given method to execute.
     */
    public void setOnMenuButtonClickListener(OnMenuButtonClickListener listener) {
        this.listener = listener;
    }
}
