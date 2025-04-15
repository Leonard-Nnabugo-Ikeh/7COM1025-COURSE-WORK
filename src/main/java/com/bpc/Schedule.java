package com.bpc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private String dateTime;
    private String physioId;
    private Treatment treatment;

    public Schedule(String dateTime, String physioId, Treatment treatment) {
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTime));
        this.physioId = physioId;
        this.treatment = treatment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPhysioId() {
        return physioId;
    }

    public Treatment getTreatment() {
        return treatment;
    }
}
