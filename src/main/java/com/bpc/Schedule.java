package com.bpc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private final String scheduleId;
    private final String dateTime;
    private final String physioId;
    private final Treatment treatment;

    public Schedule(String dateTime, String physioId, Treatment treatment, String scheduleId) {
        this.scheduleId = scheduleId;
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

    public String getScheduleId() {return scheduleId;}
}
