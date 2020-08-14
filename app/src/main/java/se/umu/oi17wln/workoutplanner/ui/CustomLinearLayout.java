package se.umu.oi17wln.workoutplanner.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import se.umu.oi17wln.workoutplanner.R;

/**
 * Created by ArmanSo on 2017-04-17.
 * Source: https://gist.github.com/armanso/4c8023b3ee84751838469f5184f1f5dd#file-roundlinerlayoutnormal-java
 *
 * Updated by Willam Larsson on 2020-08-11 to support androidx
 */
public class CustomLinearLayout extends LinearLayout {

    /**
     * Standard constructor
     * @param context = context
     */
    public CustomLinearLayout(Context context) {
        super(context);
        initBackground();
    }


    /**
     * Constructor with given attributes for LinearLayout
     * @param context = context
     * @param attrs = custom attributes
     */
    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }


    /**
     * Constructor with given attributes for LinearLayout and styling
     * @param context = context
     * @param attrs = custom attributes
     * @param defStyleAttr = style attributes
     */
    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }


    /**
     *
     */
    private void initBackground() {
        setBackground(
                ViewUtil.generateBackgroundWithShadow(
                        this,
                        R.color.colorWhite,
                        R.dimen.card_corner_radius,
                        R.color.colorWhite,
                        R.dimen.card_elevation,
                        Gravity.BOTTOM
                )
        );
    }
}