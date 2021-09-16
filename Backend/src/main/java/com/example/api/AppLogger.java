package com.example.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Simple class to output logging info.
 */
public class AppLogger {
    public static final boolean LOG_API_CALLS = true;

    /**
     * Log a message to the console.
     *
     * @param message the message to output.
     */
    public static void log(String message) {
        if (LOG_API_CALLS) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss", Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/London"));
            String formattedDate = dateFormat.format(new Date());
            System.out.println("[INFO] (" + formattedDate + ") " + message);
        }
    }
}