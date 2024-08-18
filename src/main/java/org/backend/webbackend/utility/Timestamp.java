package org.backend.webbackend.utility;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timestamp {
    public static void printWithTimestamp(String message) {
        // Get the current time
        LocalTime now = LocalTime.now();
        // Format the time as Hour:Minute:Second
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timestamp = now.format(formatter);
        // Print the message with the timestamp
        System.out.println("[" + timestamp + "] " + message);
    }
}
