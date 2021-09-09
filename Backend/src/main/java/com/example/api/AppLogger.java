package com.example.api;

import com.example.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Simple class to output logging info.
 */
public class AppLogger {
    public static final boolean LOG_API_CALLS = true;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

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

    /**
     * Log an SQL statement, just for better semantics.
     *
     * @param sql the SQL statement being executed.
     */
    public static void sql(String sql) {
        log("Executing SQL Statement:\n" + sql + "\n\n");
    }

    public static void welcome() {
        String title = "Basketball Simulator";
        String message = "© Ernest Nkansah-Badu 2021. All Rights Reserved.";
        int dashes = message.length();
        for (int i = 0; i < (dashes - title.length()) / 2; i++) {
            System.out.print(" ");
        }
        System.out.print(title + "\n");
        for (int topDashes = 0; topDashes < dashes; topDashes++) {
            System.out.print("-");
        }
        System.out.print("\n");
        System.out.println(message);
        for (int bottomDashes = 0; bottomDashes < dashes; bottomDashes++) {
            System.out.print("-");
        }
        System.out.println();
    }
}