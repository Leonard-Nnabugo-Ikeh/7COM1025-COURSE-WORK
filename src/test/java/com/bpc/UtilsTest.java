package com.bpc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testFormatToDisplayDate() {
        System.out.println("formatToDisplayDate");
        Utils instance = new Utils();

        String dateTime = "2025-02-03 10";
        String expected = "Monday 3rd of February 2025, 10:00-11:00";
        String result = instance.formatToDisplayDate(dateTime);

        dateTime = "2025-02-06 09";
        expected = "Thursday 6th of February 2025, 09:00-10:00";
        result = instance.formatToDisplayDate(dateTime);

        dateTime = "2025-02-20 16";
        expected = "Thursday 20th of February 2025, 16:00-17:00";
        result = instance.formatToDisplayDate(dateTime);
    }
}