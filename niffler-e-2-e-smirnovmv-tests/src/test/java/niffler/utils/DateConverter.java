package niffler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);

    public static Date dateFromString(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            LOGGER.error("Wrong date string input: [{}]", date);
            throw new RuntimeException(e);
        }
    }

    public static String dateToString(Date date) {
        return sdf.format(date);
    }
}
