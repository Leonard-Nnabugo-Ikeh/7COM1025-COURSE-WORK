package com.bpc;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Utils {
    private static String getDayOrdinal(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    public String formatToDisplayDate(String dateTime) {
        LocalDate date = getDateFromString(dateTime);

        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int dayOfMonth = date.getDayOfMonth();
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = date.getYear();

        String ordinal = getDayOrdinal(dayOfMonth);

        return String.format("%s %d%s of %s %d", dayOfWeek, dayOfMonth, ordinal, month, year) + ", " + getTimeRangeFromString(dateTime);
    }

    private LocalDate getDateFromString(String dateTime) {
        String date = dateTime.split(" ")[0];
        return LocalDate.parse(date);
    }

    private String getTimeRangeFromString(String dateTime) {
        String startTime = dateTime.split(" ")[1];
        String endTime = (Integer.parseInt(startTime) + 1) + "";
        return startTime + ":00" + "-" + endTime + ":00";
    }
}
