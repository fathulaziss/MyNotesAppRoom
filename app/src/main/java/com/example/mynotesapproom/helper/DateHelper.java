package com.example.mynotesapproom.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    // Method to get the current date and time as a string
    public static String getCurrentDate() {
        // Create a SimpleDateFormat instance with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

        // Get the current date and time
        Date date = new Date();

        // Return the formatted date as a string
        return dateFormat.format(date);
    }
}
