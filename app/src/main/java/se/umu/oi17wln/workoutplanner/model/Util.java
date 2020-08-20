package se.umu.oi17wln.workoutplanner.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static final String DATE_FORMAT_ABSTRACT = "####-##-##";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Get current date formatted as standard DATE_FORMAT
     * @return = current date on Util.DATE_FORMAT-format
     */
    public static String getCurrentDate(){
        return new SimpleDateFormat(Util.DATE_FORMAT, Locale.US)
                .format(new Date());
    }
}
